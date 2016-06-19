package com.github.haschi.dominium.infrastructure.commandbus;

import com.github.haschi.dominium.testdomaene.ImmutableÄndereZustand;
import com.github.haschi.dominium.testdomaene.generiert.ImmutableÄndereZustandCommand;
import com.github.haschi.dominium.testdomaene.generiert.TestAggregatEventStore;
import com.github.haschi.dominium.testdomaene.ÄndereZustand;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CdiTestRunner.class)
@SuppressWarnings("all")
@ApplicationScoped
public class CdiCommandBusSpielchenTest {

    @Inject
    private CdiCommandBus commandBus;

    private long zustand = 0L;

    @Inject
    private TestCommandHandler handler;
    private boolean called = false;

    @Inject @Testing
    private TestAggregatEventStore eventStore;

    @Test
    public void Command_wird_an_Service_weitergeleietet() {
        final UUID identitätsmerkmal = UUID.randomUUID();
        final ImmutableÄndereZustandCommand command = ImmutableÄndereZustandCommand.of(ImmutableÄndereZustand.of(
            identitätsmerkmal,
            42L));

        commandBus.send(command);

        assertThat(this.handler.getZustand()).isEqualTo(42L);
        assertThat(this.called).isTrue();
        assertThat(this.eventStore.getEventsForAggregate(identitätsmerkmal).events())
            .hasSize(1);
    }

    public void handle(@Observes final ÄndereZustand command) {
        this.called = true;
    }
}
