package com.github.haschi.dominium.persistenz.atom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.dominium.modell.Versionsbereich;
import com.github.haschi.dominium.persistenz.Ereignislager;
import com.github.haschi.dominium.persistenz.KonkurrierenderZugriff;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtomEreignisLager<T>        implements Ereignislager<UUID, T> {

    final ObjectMapper mapper;
    final OkHttpClient client;

    public AtomEreignisLager() {
        super();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.client = new OkHttpClient();
    }

    @Override
    public final void neuenEreignisstromErzeugen(
            final UUID identitätsmerkmal,
            final Collection<Domänenereignis<T>> domänenereignisse) {

        this.ereignisseSpeichern(identitätsmerkmal, domänenereignisse);
    }

    private void ereignisseSpeichern(
            final UUID identitätsmerkmal,
            final Collection<Domänenereignis<T>> domänenereignisse) {
        for (final Domänenereignis<T> ereignis : domänenereignisse) {

            final String json;
            try {
                json = this.mapper.writeValueAsString(ereignis);
            } catch (final JsonProcessingException exception) {
                throw new EreignisNichtSerialisierbar(ereignis, exception);
            }


            final MediaType jsonMediaType = MediaType.parse("application/json");
            final RequestBody requestBody = RequestBody.create(jsonMediaType, json);
            final Request request;
            try {
                request = new Builder()
                        .url(this.streamUrl(identitätsmerkmal))
                        .header("ES-EventId", UUID.randomUUID().toString())
                        .header("ES-EventType", URLEncoder.encode(ereignis.getClass().getCanonicalName(), "UTF-8"))
                        .post(requestBody)
                        .build();
            } catch (final UnsupportedEncodingException | MalformedURLException ausnahme) {
                throw new EreignisNichtGespeichert(ereignis, ausnahme);
            }

            final Response response;
            try {
                response = this.client.newCall(request).execute();
            } catch (final IOException e) {
                throw new EreignisNichtGespeichert(ereignis, e);
            }

            if (!response.isSuccessful()) {
                throw new EreignisNichtGespeichert(ereignis, response.message());
            }
        }
    }

    private URL streamUrl(final UUID identitätsmerkmal) throws MalformedURLException {
        return new URL("http://127.0.0.1:2113/streams/" + identitätsmerkmal.toString());
    }

    @Override
    public final void ereignisseDemStromHinzufügen(
            final UUID identitätsmerkmal,
            final long erwarteteVersion,
            final Collection<Domänenereignis<T>> domänenereignisse) throws KonkurrierenderZugriff {

        this.ereignisseSpeichern(identitätsmerkmal, domänenereignisse);
    }

    @Override
    public final List<Domänenereignis<T>> getEreignisliste(
            final UUID identitätsmerkmal,
            final Versionsbereich bereich) {

        boolean weiter = false;

        try {
            Ereignisstrom ereignisstrom = this.eineStromSeiteLesen(this.streamUrl(identitätsmerkmal));
            if (ereignisstrom.headOfStream) {
                final Optional<Links> last = ereignisstrom.links.stream()
                        .filter(link -> link.relation.equals("last"))
                        .findFirst();

                if (last.isPresent()) {
                    ereignisstrom = this.eineStromSeiteLesen(last.get().uri);
                }
            }

            Stream<Eintrag> alleEreignisse = Stream.empty();

            do {
                alleEreignisse = Stream.concat(alleEreignisse, ereignisstrom.entries.stream());

                weiter = !(ereignisstrom.headOfStream || (ereignisstrom == Ereignisstrom.LEER));
                if (weiter) {
                    ereignisstrom = this.eineStromSeiteLesen(ereignisstrom.links.stream()
                            .filter(link -> link.relation.equals("previous"))
                            .findFirst().get().uri);
                }
            } while (weiter);

            return alleEreignisse
                    .sorted((entry, t1) -> entry.updated.compareTo(t1.updated))
                    .map(this::ereignisLaden)
                    .collect(Collectors.toList());


        } catch (final MalformedURLException ausnahme) {
            throw new EreignisstromNichtLesbar(ausnahme);
        }
    }

    private Ereignisstrom eineStromSeiteLesen(final URL url) {
        final Request request = new Builder()
                .header("Accept", "application/vnd.eventstore.atom+json")
                .url(url)
                .build();
        final Response response;
        try {
            response = this.client.newCall(request).execute();
        } catch (final IOException ausnahme) {
            throw new EreignisstromNichtLesbar(ausnahme);
        }

        final String body;
        try {
            body = response.body().string();

            if (StringUtils.isEmpty(body)) {
                return Ereignisstrom.LEER;
            }

            return this.mapper.readValue(body, Ereignisstrom.class);
        } catch (final IOException ausnahme) {
            throw new EreignisstromNichtLesbar(ausnahme);
        }
    }

    private Domänenereignis<T> ereignisLaden(final Eintrag eintrag) {
        final Request request = new Builder()
                .header("Accept", "application/json")
                .url(eintrag.id.toString())
                .build();
        final Response response;
        try {
            response = this.client.newCall(request).execute();
            final String json = response.body().string();
            final Class<?> ereignistyp = this.ereignistypAusEintrag(eintrag);
            final Object event = this.mapper.readValue(json, ereignistyp);
            return (Domänenereignis<T>) event;

        } catch (final IOException | ClassNotFoundException ausnahme) {
            throw new EreignisstromNichtLesbar(ausnahme);
        }
    }

    private Class<?> ereignistypAusEintrag(final Eintrag eintrag) throws ClassNotFoundException {
        final String klassenname = URLDecoder.decode(eintrag.summary);
        return Class.forName(klassenname);
    }

    @Override
    public final Stream<UUID> getEreignisströme() {
        return null;
    }

    @Override
    public final boolean existiertEreignisStrom(final UUID identitätsmerkmal) {
        return false;
    }
}