package com.github.haschi.dominium.haushaltsbuch.core.model

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneHaushaltsbuchführung
import com.github.haschi.dominium.haushaltsbuch.core.model.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.dominium.haushaltsbuch.core.model.events.HaushaltsbuchführungBegonnen
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanzkonto
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler

class Haushaltsbuch
{

    @AggregateIdentifier
    private var id: Aggregatkennung? = null

    constructor()

    @CommandHandler
    constructor(anweisung: BeginneHaushaltsbuchführung)
    {
        AggregateLifecycle.apply(HaushaltsbuchführungBegonnen(anweisung.id))

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

        AggregateLifecycle.apply(EröffnungsbilanzkontoErstellt(eröffnungsbilanz))
    }

    @EventSourcingHandler
    fun falls(ereignis: HaushaltsbuchführungBegonnen)
    {
        id = ereignis.id
    }
}
