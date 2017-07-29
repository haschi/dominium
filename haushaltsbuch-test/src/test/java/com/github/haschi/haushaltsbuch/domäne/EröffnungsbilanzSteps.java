package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractEröffnungsbilanzSteps;
import com.github.haschi.haushaltsbuch.InventarZustand;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.Buchung;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;

import java.util.List;

public class EröffnungsbilanzSteps implements AbstractEröffnungsbilanzSteps
{

    private final Ereignismonitor monitor;

    public EröffnungsbilanzSteps(final Ereignismonitor monitor) {

        this.monitor = monitor;
    }

    @Override
    public void erstellen(final InventarZustand inventar)
    {

    }

    @Override
    public void erstellt(final List<Buchung> buchungen)
    {
//        assertThat(monitor.erwartetesEreignis(ImmutableEröffnungsbilanzkontoErstellt.class))
//                .
////                ImmutableEröffnungsbilanzkontoErstellt.builder.build()))
    }
}
