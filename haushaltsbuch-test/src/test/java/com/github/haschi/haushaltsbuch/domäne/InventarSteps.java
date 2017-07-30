package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractAktuellesInventarSteps;
import com.github.haschi.haushaltsbuch.AbstractInventarSteps;
import com.github.haschi.haushaltsbuch.Vermögenswert;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableInventar;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutablePosition;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import org.axonframework.config.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public class InventarSteps implements AbstractInventarSteps
{
    private final Configuration configuration;
    private final Ereignismonitor monitor;
    private final Aggregatkennung haushaltsbuchId;

    public InventarSteps(final Configuration configuration, final Ereignismonitor monitor, final Aggregatkennung haushaltsbuchId) {

        this.configuration = configuration;
        this.monitor = monitor;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public AbstractAktuellesInventarSteps anlegen(final List<Vermögenswert> vermögenswerte)
    {
        return new AktuellesInventarSteps(configuration, monitor, haushaltsbuchId,

                ImmutableInventar.builder()
                                                  .addAllVermögenswerte(vermögenswerte.stream()
                                                                                .map(vw -> ImmutablePosition
                                                                                        .builder()
                                                                                        .beschreibung(vw.position)
                                                                                        .währungsbetrag(vw.betrag)
                                                                                        .build())
                                                                                .collect(Collectors.toList()))
                                                  .build());
    }
}
