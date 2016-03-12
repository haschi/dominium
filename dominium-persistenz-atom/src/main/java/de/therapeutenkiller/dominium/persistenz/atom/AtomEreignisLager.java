package de.therapeutenkiller.dominium.persistenz.atom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.modell.Schnappschuss;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.Ereignislager;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtomEreignisLager<A extends Aggregatwurzel<A, UUID, T>, T>
        implements Ereignislager<A, UUID, T> {

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

            final ObjectMapper mapper = new ObjectMapper();
            final String json;
            try {
                json = mapper.writeValueAsString(ereignis);
            } catch (final JsonProcessingException exception) {
                throw new EreignisNichtSerialisierbar(ereignis, exception);
            }

            final OkHttpClient client = new OkHttpClient();
            final MediaType jsonMediaType = MediaType.parse("application/json");
            final RequestBody requestBody = RequestBody.create(jsonMediaType, json);
            final Request request;
            try {
                request = new Request.Builder()
                        .url(this.streamUrl(identitätsmerkmal))
                        .header("ES-EventId", UUID.randomUUID().toString())
                        .header("ES-EventType", URLEncoder.encode(ereignis.getClass().getCanonicalName(), "UTF-8"))
                        .post(requestBody)
                        .build();
            } catch (final UnsupportedEncodingException ausnahme) {
                throw new EreignisNichtGespeichert(ereignis, ausnahme);
            }

            final Response response;
            try {
                response = client.newCall(request).execute();
            } catch (final IOException e) {
                throw new EreignisNichtGespeichert(ereignis, e);
            }

            if (!response.isSuccessful()) {
                throw new EreignisNichtGespeichert(ereignis, response.message());
            }
        }
    }

    private final String streamUrl(final UUID identitätsmerkmal) {
        return "http://127.0.0.1:2113/streams/" + identitätsmerkmal.toString();
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

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Accept", "application/vnd.eventstore.atom+json")
                .url(this.streamUrl(identitätsmerkmal)/*+"?embed=body"*/)
                .build();
        final Response response;
        try {
            response = client.newCall(request).execute();
        } catch (final IOException ausnahme) {
            throw new EreignisstromNichtLesbar(ausnahme);
        }
        try {
            final String body = response.body().string();
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final Ereignisstrom ereignisstrom = mapper.readValue(body, Ereignisstrom.class);
            System.out.println(ereignisstrom.id);
            System.out.println(body);

            final List<Domänenereignis<T>> ereignisse = ereignisstrom.entries.stream()
                    .sorted((entry, t1) -> entry.updated.compareTo(t1.updated))
                    .map(e -> this.ereignisLaden(e))
                    .collect(Collectors.toList());

            return ereignisse;

        } catch (final IOException e) {
            System.out.println(e.getMessage());
            throw new EreignisstromNichtLesbar(e);
        }
    }

    private Domänenereignis<T> ereignisLaden(final Eintrag eintrag) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Accept", "application/json")
                .url(eintrag.id.toString())
                .build();
        final Response response;
        try {
            response = client.newCall(request).execute();
            final String json = response.body().string();
            final ObjectMapper mapper = new ObjectMapper();
            final Class<?> ereignistyp = this.ereignistypAusEintrag(eintrag);
            final Object event = mapper.readValue(json, ereignistyp);
            return (Domänenereignis<T>)event;

        } catch (final IOException ausnahme) {
            throw new EreignisstromNichtLesbar(ausnahme);
        } catch (final ClassNotFoundException ausnahme) {
            throw new EreignisstromNichtLesbar(ausnahme);
        }
    }

    private Class<?> ereignistypAusEintrag(final Eintrag eintrag) throws ClassNotFoundException {
        final String klassenname = URLDecoder.decode(eintrag.summary);
        return Class.forName(klassenname);
    }

    @Override
    public void schnappschussHinzufügen(
            final UUID identitätsmerkmal,
            final Schnappschuss<A, UUID> snapshot) throws AggregatNichtGefunden {

    }

    @Override
    public final Optional<Schnappschuss<A, UUID>> getNeuesterSchnappschuss(
            final UUID identitätsmerkmal) throws AggregatNichtGefunden {
        return null;
    }

    @Override
    public final Stream<UUID> getEreignisströme() {
        return null;
    }
}