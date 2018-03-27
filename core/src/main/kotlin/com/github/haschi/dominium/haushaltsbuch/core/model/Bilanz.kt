package com.github.haschi.dominium.haushaltsbuch.core.model

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.SchlageEröffnungsbilanzVor
import com.github.haschi.dominium.haushaltsbuch.core.model.events.PrivateEröffnungsbilanzVorgeschlagen
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Bilanzgruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Gruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswert
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler

class Bilanz
{
    @AggregateIdentifier
    var bilanzId: Aggregatkennung? = null;

    constructor()

    @CommandHandler
    constructor(anweisung: SchlageEröffnungsbilanzVor)
    {
        val inventar = anweisung.inventar

        val bilanz = Eröffnungsbilanz(
                listOf(
                        Bilanzgruppe(
                                Gruppe('A', "Anlagevermögen"),
                                inventar.anlagevermoegen),
                        Bilanzgruppe(
                                Gruppe('B', "Umlaufvermögen"),
                                inventar.umlaufvermoegen)
                        ),
                listOf(
                        Bilanzgruppe(
                                Gruppe('A', "Eigenkapital"),
                                Vermoegenswerte(Vermoegenswert("Eigenkapital", inventar.reinvermoegen.reinvermoegen))
                        ),
                        Bilanzgruppe(
                                Gruppe('B', "Schulden"),
                                Vermoegenswerte(inventar.schulden.map { Vermoegenswert(it.position, it.waehrungsbetrag) })
                        )
                ))

        val ereignis = PrivateEröffnungsbilanzVorgeschlagen(anweisung.bilanzId, anweisung.inventurId, bilanz)

        AggregateLifecycle.apply(ereignis)
    }

    @EventSourcingHandler
    fun falls(ereignis: PrivateEröffnungsbilanzVorgeschlagen)
    {
        this.bilanzId = ereignis.bilanzId
    }
}