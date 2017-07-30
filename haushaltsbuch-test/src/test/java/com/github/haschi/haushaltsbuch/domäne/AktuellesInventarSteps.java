package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractAktuellesInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractEröffnungsbilanzSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Inventar;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import java.util.function.Consumer;

public class AktuellesInventarSteps implements AbstractAktuellesInventarSteps
{
    private final Configuration configuration;
    private final Ereignismonitor monitor;
    private final Aggregatkennung haushaltsbuchId;
    private final Inventar inventar;

    public AktuellesInventarSteps(
            final Configuration configuration, final Ereignismonitor monitor, final Aggregatkennung
            haushaltsbuchId, final Inventar
            inventar) {
        this.configuration = configuration;

        this.monitor = monitor;
        this.haushaltsbuchId = haushaltsbuchId;
        this.inventar = inventar;
    }
    @Override
    public void eröffnungsbilanz(final Consumer<AbstractEröffnungsbilanzSteps> consumer)
    {
        consumer.accept(new EröffnungsbilanzSteps(configuration, monitor, haushaltsbuchId, inventar));
    }
}
