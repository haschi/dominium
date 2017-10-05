package org.github.haschi.haushaltsbuch.modell;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.github.haschi.haushaltsbuch.api.BeendeInventur;
import org.github.haschi.haushaltsbuch.api.BeginneInventur;
import org.github.haschi.haushaltsbuch.api.ErfasseInventar;
import org.github.haschi.haushaltsbuch.api.ErfasseSchulden;
import org.github.haschi.haushaltsbuch.api.ErfasseUmlaufvermögen;
import org.github.haschi.haushaltsbuch.api.InventarErfasst;
import org.github.haschi.haushaltsbuch.api.InventurAusnahme;
import org.github.haschi.haushaltsbuch.api.InventurBeendet;
import org.github.haschi.haushaltsbuch.api.InventurBegonnen;
import org.github.haschi.haushaltsbuch.api.SchuldErfasst;
import org.github.haschi.haushaltsbuch.api.UmlaufvermögenErfasst;
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung;

public class Inventur
{

    @AggregateIdentifier
    private Aggregatkennung id;

    private boolean inventarErfasst = false;

    private boolean beendet = false;

    public Inventur()
    {
    }

    @CommandHandler
    public Inventur(final BeginneInventur anweisung)
    {
        AggregateLifecycle.apply(InventurBegonnen.of(anweisung.id()));
    }

    @EventSourcingHandler
    public void falls(final InventurBegonnen ereignis)
    {
        id = ereignis.id();
    }

    @CommandHandler
    // TODO: löschen
    public void erfasseUmlaufvermögen(final ErfasseUmlaufvermögen anweisung)
    {
        AggregateLifecycle.apply(UmlaufvermögenErfasst.builder()
                                         .position(anweisung.position())
                                         .währungsbetrag(anweisung.währungsbetrag())
                                         .build());
    }

    @CommandHandler
    // TODO: löschen
    public void erfasseSchulden(final ErfasseSchulden anweisung)
    {
        AggregateLifecycle.apply(
                SchuldErfasst.builder()
                        .position(anweisung.position())
                        .währungsbetrag(anweisung.währungsbetrag())
                        .build());
    }

    @CommandHandler
    public void erfasseInventar(final ErfasseInventar anweisung) throws InventurAusnahme
    {
        if (beendet)
        {
            throw new InventurAusnahme("Inventur bereits beendet");
        }

        AggregateLifecycle.apply(
                InventarErfasst.builder()
                        .inventar(anweisung.inventar())
                        .build());
    }

    @EventSourcingHandler
    public void falls(final InventarErfasst ereignis)
    {
        inventarErfasst = true;
    }

    @CommandHandler
    public void beendeInventur(final BeendeInventur anweisung) throws InventurAusnahme
    {

        if (!inventarErfasst)
        {
            throw new InventurAusnahme("Kein Inventar erfasst");
        }

        AggregateLifecycle.apply(InventurBeendet.builder().build());
    }

    @EventSourcingHandler
    public void falls(final InventurBeendet ereignis)
    {
        beendet = true;
    }
}
