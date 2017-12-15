package com.github.haschi.dominium.haushaltsbuch.core.model

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeendeInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseSchulden
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseUmlaufvermögen
import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventarErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventurBeendet
import com.github.haschi.dominium.haushaltsbuch.core.model.events.InventurBegonnen
import com.github.haschi.dominium.haushaltsbuch.core.model.events.SchuldErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.events.UmlaufvermögenErfasst
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurAusnahme
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler

class Inventur
{
    private enum class Lebenszyklus
    {
        BEGONNEN, ERFASST, BEENDET
    }

    @AggregateIdentifier
    private var id: Aggregatkennung? = null

    private var lebenszyklus = Lebenszyklus.BEGONNEN

    constructor()

    @CommandHandler constructor(anweisung: BeginneInventur)
    {
        AggregateLifecycle.apply(InventurBegonnen(anweisung.id))
    }

    @EventSourcingHandler
    fun falls(ereignis: InventurBegonnen)
    {
        id = ereignis.id
        lebenszyklus = Lebenszyklus.BEGONNEN
    }

    @CommandHandler // TODO: löschen
    fun erfasseUmlaufvermögen(anweisung: ErfasseUmlaufvermögen)
    {
        AggregateLifecycle.apply(UmlaufvermögenErfasst(
                position = anweisung.position,
                betrag = anweisung.währungsbetrag))
    }

    @CommandHandler
    fun erfasseSchulden(anweisung: ErfasseSchulden)
    {
        AggregateLifecycle.apply(
                SchuldErfasst(
                        position = anweisung.position,
                        betrag = anweisung.währungsbetrag))
    }

    @CommandHandler
    @Throws(InventurAusnahme::class)
    fun erfasseInventar(anweisung: ErfasseInventar)
    {
        if (lebenszyklus == Lebenszyklus.BEENDET)
        {
            throw InventurAusnahme("Inventur bereits beendet")
        }

        AggregateLifecycle.apply(InventarErfasst(anweisung.inventar))
    }

    @EventSourcingHandler
    fun falls(ereignis: InventarErfasst)
    {
        lebenszyklus = Lebenszyklus.ERFASST
    }

    @CommandHandler
    @Throws(InventurAusnahme::class)
    fun beendeInventur(anweisung: BeendeInventur)
    {
        if (lebenszyklus != Lebenszyklus.ERFASST)
        {
            throw InventurAusnahme("Kein Inventar erfasst")
        }

        AggregateLifecycle.apply(InventurBeendet())
    }

    @EventSourcingHandler
    fun falls(ereignis: InventurBeendet)
    {
        lebenszyklus = Lebenszyklus.BEENDET
    }
}
