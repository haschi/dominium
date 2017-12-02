package org.github.haschi.haushaltsbuch.modell

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.github.haschi.haushaltsbuch.modell.core.commands.BeginneHaushaltsbuchführung
import org.github.haschi.haushaltsbuch.modell.core.events.EröffnungsbilanzkontoErstellt
import org.github.haschi.haushaltsbuch.modell.core.events.HaushaltsbuchführungBegonnen
import org.github.haschi.haushaltsbuch.modell.core.values.Buchung
import org.github.haschi.haushaltsbuch.modell.core.values.Eröffnungsbilanzkonto
import org.github.haschi.haushaltsbuch.modell.core.values.Währungsbetrag
import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung

class Haushaltsbuch {

    @AggregateIdentifier
    private var id: Aggregatkennung? = null

    constructor()

    @CommandHandler
    constructor(anweisung: BeginneHaushaltsbuchführung) {
        AggregateLifecycle.apply(
                HaushaltsbuchführungBegonnen(anweisung.id))

        val inventar = anweisung.inventar

        val eigenkapital = Währungsbetrag(
                inventar.anlagevermoegen.summe.wert
                        .add(inventar.umlaufvermoegen.summe.wert)
                // .subtract(inventar.schulden().summe().wert())
        )

        val eröffnungsbilanz = Eröffnungsbilanzkonto(
                haben = listOf(
                        Buchung(
                                buchungstext = "Anlagevermögen (AV)",
                                betrag = inventar.anlagevermoegen.summe),
                        Buchung(
                                buchungstext = "Umlaufvermögen (UV)",
                                betrag = inventar.umlaufvermoegen.summe)),
                soll = listOf(
                        Buchung(
                                buchungstext = "Eigenkapital (EK)",
                                betrag = eigenkapital)))

        //                .addSoll(Buchung.builder()
        //                                 .buchungstext("Fremdkapital (FK)")
        //                                 .betrag(inventar.schulden().summe())
        //                                 .build())


        AggregateLifecycle.apply(
                EröffnungsbilanzkontoErstellt(eröffnungsbilanz))
    }

    @EventSourcingHandler
    fun falls(ereignis: HaushaltsbuchführungBegonnen) {
        id = ereignis.id
    }
}
