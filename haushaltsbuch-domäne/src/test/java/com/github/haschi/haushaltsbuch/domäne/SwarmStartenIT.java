package com.github.haschi.haushaltsbuch.domäne;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.swarm.Swarm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SwarmStartenIT
{
    private Swarm swarm;

    @Before
    public void swarm_erzeugen() throws Exception
    {
        swarm = Main.createSwarm();
    }

    @Test
    public void kann_swarm_starten() {

        final Throwable thrown = catchThrowable(() -> swarm.start());

        assertThat(thrown).doesNotThrowAnyException();
    }

    @Test
    public void kann_anwendung_in_den_swarm_deployen() throws Exception
    {
        swarm.start();

        final Throwable thrown = catchThrowable(() -> swarm.deploy());

        assertThat(thrown).doesNotThrowAnyException();
    }

    @After
    public void swarm_stoppen() throws Exception
    {
        swarm.stop();
    }
}
