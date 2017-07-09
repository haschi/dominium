package com.github.haschi.haushaltsbuch.domäne;

import org.junit.Before;
import org.junit.Test;
import org.wildfly.swarm.Swarm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SwarmStoppenIT
{
    private Swarm swarm;

    @Before
    public void swarm_erzeugen() throws Exception
    {
        swarm = Main.createSwarm();
    }

    @Test
    public void kann_swarm_beenden() throws Exception
    {
        swarm.start();
        swarm.deploy();

        final Throwable thrown = catchThrowable(() -> swarm.stop());

        assertThat(thrown).doesNotThrowAnyException();
    }
}
