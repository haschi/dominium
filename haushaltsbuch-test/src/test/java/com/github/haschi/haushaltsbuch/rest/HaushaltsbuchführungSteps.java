package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractAktuellesHaushaltsbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHaushaltsbuchführungSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableBefehleAnweisung;
import org.axonframework.config.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

public final class HaushaltsbuchführungSteps implements AbstractHaushaltsbuchführungSteps
{

    private final Configuration konfiguration;
    private final Synchronisierungsmonitor monitor;
    private AktuellesHaushaltsbuchStep aktuellesHaushaltsbuch;

    public HaushaltsbuchführungSteps(final Configuration konfiguration, final Synchronisierungsmonitor monitor)
    {
        this.konfiguration = konfiguration;
        this.monitor = monitor;
    }

    @Override
    public void beginnen()
    {
        final Aggregatkennung kennung = Aggregatkennung.neu();
        final BeginneHaushaltsbuchführung beginneHaushaltsbuchführung = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(kennung)
                .build();

        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
                e.printStackTrace();
        }

        // konfiguration.commandGateway().sendAndWait(ImmutableBefehleAnweisung.builder().zahl(42).build());

        konfiguration.commandGateway().sendAndWait(beginneHaushaltsbuchführung);
        monitor.erwarte(3);

        this.aktuellesHaushaltsbuch = new AktuellesHaushaltsbuchStep(monitor, kennung);
    }

    private Optional<AbstractAktuellesHaushaltsbuchSteps> getAktuell() {
        return Optional.ofNullable(this.aktuellesHaushaltsbuch);
    }

    @Override
    public void aktuellesHaushaltsbuch(final Consumer<AbstractAktuellesHaushaltsbuchSteps> consumer)
    {
        consumer.accept(getAktuell().orElseThrow(IllegalStateException::new));
    }
}
