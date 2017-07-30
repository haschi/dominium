package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractAktuellesHaushaltsbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.AbstractKontenrahmenSteps;
import com.github.haschi.haushaltsbuch.StepBuilder;
import com.github.haschi.haushaltsbuch.StepConsumer;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;
import cucumber.api.PendingException;

import java.util.function.Consumer;

public class AktuellesHaushaltsbuchStep implements AbstractAktuellesHaushaltsbuchSteps
{
    private final Ereignismonitor monitor;
    private final Aggregatkennung haushaltsbuchId;

    public AktuellesHaushaltsbuchStep(final Ereignismonitor monitor, final Aggregatkennung haushaltsbuchId) {

        this.monitor = monitor;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void hauptbuch(final Consumer<AbstractHauptbuchSteps> consumer)
    {
        consumer.accept(new HauptbuchSteps(monitor, haushaltsbuchId));
    }

    @Override
    public void inventar(final StepBuilder builder)
    {
        throw new PendingException();
    }

    @Override
    public void inventar(final StepConsumer consumer)
    {
        throw new PendingException();
    }

    @Override
    public void journal(final Consumer<AbstractJournalSteps> consumer)
    {
        throw new PendingException();
    }

    @Override
    public void kontenrahmen(final Consumer<AbstractKontenrahmenSteps> consumer)
    {
        throw new PendingException();
    }
}
