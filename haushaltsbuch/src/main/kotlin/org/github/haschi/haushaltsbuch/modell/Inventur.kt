package org.github.haschi.haushaltsbuch.modell

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.messaging.annotation.MetaDataValue
import org.github.haschi.haushaltsbuch.api.commands.BeginneInventur
import org.github.haschi.haushaltsbuch.api.commands.ErfasseInventar
import org.github.haschi.haushaltsbuch.api.commands.ErfasseSchulden
import org.github.haschi.haushaltsbuch.api.commands.ErfasseUmlaufvermögen
import org.github.haschi.haushaltsbuch.api.events.BeendeInventur
import org.github.haschi.haushaltsbuch.api.events.InventarErfasst
import org.github.haschi.haushaltsbuch.api.events.InventurBeendet
import org.github.haschi.haushaltsbuch.api.events.InventurBegonnen
import org.github.haschi.haushaltsbuch.api.events.SchuldErfasst
import org.github.haschi.haushaltsbuch.api.events.UmlaufvermögenErfasst
import org.github.haschi.haushaltsbuch.core.InventurAusnahme
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

class Inventur {
    @AggregateIdentifier
    private var id: Aggregatkennung? = null

    private var inventarErfasst = false

    private var beendet = false

    constructor()

    @CommandHandler
    constructor(anweisung: BeginneInventur, @MetaDataValue("threadId") threadId: Long) {
        if (Thread.currentThread().id != threadId) {
            throw IllegalThreadStateException()
        }

        AggregateLifecycle.apply(InventurBegonnen(anweisung.id))
    }

    @EventSourcingHandler
    fun falls(ereignis: InventurBegonnen) {
        id = ereignis.id
    }

    @CommandHandler
            // TODO: löschen
    fun erfasseUmlaufvermögen(anweisung: ErfasseUmlaufvermögen) {
        AggregateLifecycle.apply(UmlaufvermögenErfasst(
                position = anweisung.position,
                betrag = anweisung.währungsbetrag))
    }

    @CommandHandler
            // TODO: löschen
    fun erfasseSchulden(anweisung: ErfasseSchulden) {
        AggregateLifecycle.apply(
                SchuldErfasst(
                        position = anweisung.position,
                        betrag = anweisung.währungsbetrag))
    }

    @CommandHandler
    @Throws(InventurAusnahme::class)
    fun erfasseInventar(anweisung: ErfasseInventar) {
        if (beendet) {
            throw InventurAusnahme("Inventur bereits beendet")
        }

        AggregateLifecycle.apply(InventarErfasst(anweisung.inventar))
    }

    @EventSourcingHandler
    fun falls(ereignis: InventarErfasst) {
        inventarErfasst = true
    }

    @CommandHandler
    @Throws(InventurAusnahme::class)
    fun beendeInventur(anweisung: BeendeInventur) {

        if (!inventarErfasst) {
            throw InventurAusnahme("Kein Inventar erfasst")
        }

        AggregateLifecycle.apply(InventurBeendet())
    }

    @EventSourcingHandler
    fun falls(ereignis: InventurBeendet) {
        beendet = true
    }
}
