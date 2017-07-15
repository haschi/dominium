package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.HaushaltsbuchAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.testcontrol.api.TestControl;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventstore.EventStore;
import org.axonframework.unitofwork.DefaultUnitOfWork;
import org.axonframework.unitofwork.UnitOfWork;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CdiTestRunner.class)
@TestControl(projectStage = ProjectStage.UnitTest.class)
public class HauptbuchAbfrageTest
{

    @Inject
    CommandGateway commandGateway;

    @Inject
    HauptbuchAbfrage abfrage;

    @Inject
    EventStore eventStore;
    private HaushaltsbuchAngelegt event;

    @Test
    public void hauptbuchAbfragen()
    {
        final UnitOfWork uof = DefaultUnitOfWork.startAndGet();
        final Aggregatkennung haushaltsbuchId = Aggregatkennung.neu();
        this.commandGateway.sendAndWait(ImmutableBeginneHaushaltsbuchführung.builder().id(haushaltsbuchId).build());
        uof.commit();

        final HauptbuchAnsicht abfragen = this.abfrage.abfragen(haushaltsbuchId);

        assertThat(abfragen).isEqualTo(ImmutableHauptbuchAnsicht.builder()
                                               .haushaltsbuchId(haushaltsbuchId)
                                               .addAktivkonten("Anfangsbestand")
                                               .build());
    }
}
