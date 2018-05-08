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

    @Suppress("unused")
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
    fun erfasseUmlaufvermoegen(anweisung: ErfasseUmlaufvermögen)
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
                        betrag = anweisung.waehrungsbetrag))
    }

    @CommandHandler
    @Throws(InventurAusnahme::class)
    fun erfasseInventar(anweisung: ErfasseInventar)
    {
        if (lebenszyklus == Lebenszyklus.BEENDET)
        {
            throw InventurAusnahme("Inventur bereits beendet")
        }

        AggregateLifecycle.apply(InventarErfasst(
                anweisung.anlagevermoegen,
                anweisung.umlaufvermoegen,
                anweisung.schulden))
    }

    @EventSourcingHandler
    fun falls(@Suppress("UNUSED_PARAMETER") ereignis: InventarErfasst)
    {
        lebenszyklus = Lebenszyklus.ERFASST
    }

    @CommandHandler
    @Throws(InventurAusnahme::class)
    fun beendeInventur(@Suppress("UNUSED_PARAMETER") anweisung: BeendeInventur, zeit: Zeit)
    {
        if (lebenszyklus != Lebenszyklus.ERFASST)
        {
            throw InventurAusnahme("Kein Inventar erfasst")
        }

        AggregateLifecycle.apply(InventurBeendet(id!!, zeit.jetzt))
    }

    @EventSourcingHandler
    fun falls(@Suppress("UNUSED_PARAMETER") ereignis: InventurBeendet)
    {
        lebenszyklus = Lebenszyklus.BEENDET
    }
}
