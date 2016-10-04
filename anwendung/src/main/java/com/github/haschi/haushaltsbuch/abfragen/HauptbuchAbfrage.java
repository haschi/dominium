package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.axonframework.domain.DomainEventMessage;
import org.axonframework.eventhandling.annotation.EventHandler;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("checkstyle:designforextension")

@Transactional
public class HauptbuchAbfrage
{

    @Inject
    private EntityManager entityManager;

    public HauptbuchAnsicht abfragen(final UUID haushaltsbuchId)
    {

        final TypedQuery<Konto> query = this.entityManager.createQuery("select k from Konto k group by k.kontoart",
                Konto.class);

        final List<Konto> konten = query.getResultList();

        return ImmutableHauptbuchAnsicht.builder()
                .addAllAktivkonten(this.kontenliste(konten.stream(), Kontoart.Aktiv))
                .build();
    }

    private List<String> kontenliste(final Stream<Konto> alle, final Kontoart kontoart)
    {
        return alle.filter(k -> k.kontoart == kontoart).map(k -> k.id.kontoname).collect(Collectors.toList());
    }

    @EventHandler
    public void falls(final KontoWurdeAngelegt ereignis, final DomainEventMessage<KontoWurdeAngelegt> message)
    {
        final KontoId id = new KontoId();
        id.kontoname = ereignis.kontoname();
        id.haushaltsbuch = UUID.fromString(message.getAggregateIdentifier().toString());

        final Konto k = new Konto();
        k.id = id;
        k.kontoart = ereignis.kontoart();
        k.saldo = BigDecimal.ZERO;
        k.währung = "EUR";

        this.entityManager.persist(k);
    }

    @EventHandler
    public void falls(final HauptbuchWurdeAngelegt ereignis)
    {
        System.out.println(ereignis.haushaltsbuchId());
    }
}
