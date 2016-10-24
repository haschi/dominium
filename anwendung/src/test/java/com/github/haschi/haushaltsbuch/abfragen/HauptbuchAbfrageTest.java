package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.EineDomainCdiBean;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.ereignis.HaushaltsbuchAngelegt;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.testcontrol.api.TestControl;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CdiTestRunner.class)
@TestControl(projectStage = ProjectStage.UnitTest.class)
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

    @Inject
    ProjectStage stage;

    @Test
    public void hauptbuchAbfragen()
    {
        final UUID haushaltsbuchId = UUID.randomUUID();
        this.commandGateway.sendAndWait(ImmutableBeginneHaushaltsbuchfuehrung.builder().id(haushaltsbuchId).build());

        this.entityManager.flush();
        this.entityManager.clear();

        final HauptbuchAnsicht abfragen = this.abfrage.abfragen(haushaltsbuchId);

        assertThat(abfragen).isEqualTo(ImmutableHauptbuchAnsicht.builder()
                .haushaltsbuchId(haushaltsbuchId)
                .addAktivkonten("Anfangsbestand")
                .build());

        assertThat(this.stage).isEqualTo(ProjectStage.UnitTest);
        
        this.entityManager.clear();
    }

    @Test
    public void persistenzTesten()
    {
        final KontoId id = new KontoId();
        id.kontoname = "Hello";
        id.haushaltsbuch = UUID.randomUUID();

        final Konto konto = new Konto();
        konto.id = id;
        konto.kontoart = Kontoart.Aktiv;
        konto.währung = "EUR";
        konto.saldo = BigDecimal.ZERO;

        this.entityManager.persist(konto);
        //entityManager.getTransaction().begin();
        this.entityManager.flush();
        //entityManager.getTransaction().commit();

    }

    @Test
    public void EinTest()
    {
        this.bean.testTransaction();
    }

    @EventHandler
    public void falls(final HaushaltsbuchAngelegt ereginis)
    {
        System.out.println("Beginne Haushaltsbuchführen");
    }
}
