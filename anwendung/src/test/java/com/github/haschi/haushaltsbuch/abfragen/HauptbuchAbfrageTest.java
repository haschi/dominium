package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.EineDomainCdiBean;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HaushaltsbuchAngelegt;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

@RunWith(CdiTestRunner.class)
@Transactional
public class HauptbuchAbfrageTest
{

    @Inject
    CommandGateway commandGateway;

    @Inject
    HauptbuchAbfrage abfrage;

    @Inject
    ExampleCdiBean bean;

    @Inject
    AnotherCdiBean other;

    @Inject
    EntityManager entityManager;

    @Inject
    EineDomainCdiBean xbean;

    @Test
    public void hauptbuchAbfragen()
    {
        final UUID haushaltsbuchId = UUID.randomUUID();
        this.commandGateway.sendAndWait(ImmutableBeginneHaushaltsbuchfuehrung.builder().id(haushaltsbuchId).build());

        this.entityManager.flush();
        this.entityManager.clear();

        //        final HauptbuchAnsicht abfragen = this.abfrage.abfragen(haushaltsbuchId);
        //
        //        assertThat(abfragen).asList()
        //                            .isEmpty();
        //        //
        //        //        this.entityManager.clear();
    }

    @Test
    public void persistenzTesten()
    {
        KontoId id = new KontoId();
        id.kontoname = "Hello";
        id.haushaltsbuch = UUID.randomUUID();

        Konto konto = new Konto();
        konto.id = id;
        konto.kontoart = Kontoart.Aktiv;
        konto.währung = "EUR";
        konto.saldo = BigDecimal.ZERO;

        entityManager.persist(konto);
        //entityManager.getTransaction().begin();
        entityManager.flush();
        //entityManager.getTransaction().commit();

    }

    @Test
    public void EinTest()
    {
        bean.testTransaction();
    }

    @EventHandler
    public void falls(final HaushaltsbuchAngelegt ereginis)
    {
        System.out.println("Beginne Haushaltsbuchführen");
    }
}
