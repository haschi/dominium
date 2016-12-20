package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.Kontoname;
import com.github.haschi.haushaltsbuch.api.ereignis.*;
import com.github.haschi.haushaltsbuch.api.kommando.*;
import com.github.haschi.haushaltsbuch.domaene.aggregat.konto.HabenkontoSpezifikation;
import com.github.haschi.haushaltsbuch.domaene.aggregat.konto.SollkontoSpezifikation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import javax.money.MonetaryAmount;
import java.util.UUID;

public final class Haushaltsbuch
        extends AbstractAnnotatedAggregateRoot<UUID>
{

    private static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";
    private static final long serialVersionUID = -4864886584911078356L;

    private Journal journal;
    private Hauptbuch hauptbuch;

    @AggregateIdentifier
    private UUID id;

    protected Haushaltsbuch()
    {
        super();
    }

    @CommandHandler
    public Haushaltsbuch(final ImmutableBeginneHaushaltsbuchfuehrung befehl)
    {
        super();

        this.apply(ImmutableHaushaltsbuchAngelegt.of(befehl.id()));
        this.apply(ImmutableJournalWurdeAngelegt.builder().aktuelleHaushaltsbuchId(befehl.id()).build());
        this.apply(ImmutableHauptbuchWurdeAngelegt.builder().haushaltsbuchId(befehl.id()).build());

        this.apply(ImmutableKontoWurdeAngelegt.builder()
                           .kontoart(Kontoart.Aktiv)
                           .kontoname(Konto.ANFANGSBESTAND.getName().toString())
                           .build());
    }

    @CommandHandler
    public void einnahmeBuchen(final ImmutableBucheEinnahme befehl)
    {
        this.buchungssatzHinzufügen(new Buchungssatz(
                Kontoname.of(befehl.sollkonto()),
                Kontoname.of(befehl.habenkonto()),
                befehl.waehrungsbetrag()));
    }

    // Hauptbuch -- Alle Methoden zum Hauptbuch

    @CommandHandler
    public void neuesKontoHinzufügen(final ImmutableLegeKontoAn befehl)
    {
        if (this.hauptbuch.istKontoVorhanden(Kontoname.of(befehl.kontoname())))
        {
            this.apply(ImmutableKontoWurdeNichtAngelegt.builder()
                               .kontoname(befehl.kontoname())
                               .kontoart(befehl.kontoart())
                               .build());
        }
        else
        {
            this.apply(ImmutableKontoWurdeAngelegt.builder()
                               .kontoname(befehl.kontoname())
                               .kontoart(befehl.kontoart())
                               .build());

            this.apply(ImmutableSaldoWurdeGeaendert.builder()
                               .kontoname(befehl.kontoname())
                               .neuerSaldo(new SollHabenSaldo())
                               .build());
        }
    }

    @CommandHandler
    public void kontoMitAnfangsbestandAnlegen(final ImmutableLegeKontoMitAnfangsbestandAn befehl)
    {
        if (this.hauptbuch.istKontoVorhanden(Kontoname.of(befehl.kontoname())))
        {
            this.apply(ImmutableKontoWurdeNichtAngelegt.builder()
                               .kontoname(befehl.kontoname())
                               .kontoart(befehl.kontoart())
                               .build());
        }
        else
        {
            this.apply(ImmutableKontoWurdeAngelegt.builder()
                               .kontoname(befehl.kontoname())
                               .kontoart(befehl.kontoart())
                               .build());

            this.anfangsbestandBuchen(ImmutableBucheAnfangsbestand.builder()
                                              .haushaltsbuchId(befehl.haushaltsbuchId())
                                              .kontoname(befehl.kontoname())
                                              .waehrungsbetrag(befehl.betrag())
                                              .build());
        }
    }

    private Saldo kontostandBerechnen(final Konto konto)
    {

        final SollkontoSpezifikation sollkonto = new SollkontoSpezifikation(konto);
        final MonetaryAmount summerDerSollBuchungen = this.journal.summeFür(sollkonto);

        final HabenkontoSpezifikation habenkonto = new HabenkontoSpezifikation(konto);
        final MonetaryAmount summerDerHabenBuchungen = this.journal.summeFür(habenkonto);

        return this.saldieren(summerDerSollBuchungen, summerDerHabenBuchungen);
    }

    private Saldo saldieren(final MonetaryAmount summerDerSollBuchungen, final MonetaryAmount summerDerHabenBuchungen)
    {
        if (summerDerHabenBuchungen.isEqualTo(summerDerSollBuchungen))
        {
            return new SollHabenSaldo();
        }

        if (summerDerHabenBuchungen.isGreaterThan(summerDerSollBuchungen))
        {
            return new Habensaldo(summerDerHabenBuchungen.subtract(summerDerSollBuchungen));
        }

        return new Sollsaldo(summerDerSollBuchungen.subtract(summerDerHabenBuchungen));
    }

    @EventSourcingHandler
    private void falls(final ImmutableHaushaltsbuchAngelegt ereignis)
    {
        this.id = ereignis.id();
    }

    @EventSourcingHandler
    public void falls(final ImmutableKontoWurdeAngelegt ereignis)
    {
        final BuchungsregelFabrik fabrik = new BuchungsregelFabrik(ereignis.kontoart());
        final Buchungsregel regel = fabrik.erzeugen(Kontoname.of(ereignis.kontoname()));

        final Konto konto = new Konto(Kontoname.of(ereignis.kontoname()), regel, ereignis.kontoart());

        this.hauptbuch.hinzufügen(konto);
    }

    @EventSourcingHandler
    public void falls(final SaldoWurdeGeaendert saldoGeaendert)
    {
        this.hauptbuch.saldoÄndern(saldoGeaendert);
    }

    @EventSourcingHandler
    public void falls(final BuchungWurdeAusgeführt ereignis)
    {
        this.journal.buchungssatzHinzufügen(ereignis.buchungssatz());
    }

    @EventSourcingHandler
    public void falls(final HauptbuchWurdeAngelegt hauptbuchWurdeAngelegt)
    {
        this.hauptbuch = new Hauptbuch();
    }

    @EventSourcingHandler
    public void falls(final JournalWurdeAngelegt journalWurdeAngelegt)
    {
        this.journal = new Journal();
    }

    @CommandHandler
    public void anfangsbestandBuchen(final ImmutableBucheAnfangsbestand befehl)
    {
        if (this.journal.istAnfangsbestandFürKontoVorhanden(this.hauptbuch.suchen(Kontoname.of(befehl.kontoname()))))
        {
            apply(ImmutableBuchungWurdeAbgelehnt.builder().grund(FEHLERMELDUNG).build());
        }
        else
        {
            final Konto konto = this.hauptbuch.suchen(Kontoname.of(befehl.kontoname()));
            final Buchungssatz buchungssatz = konto.buchungssatzFürAnfangsbestand(befehl.waehrungsbetrag());

            this.buchungssatzHinzufügen(buchungssatz);
        }
    }

    private void buchungssatzHinzufügen(final Buchungssatz buchungssatz)
    {

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz))
        {
            this.apply(ImmutableBuchungWurdeAusgeführt.builder().buchungssatz(buchungssatz).build());

            final Konto sollkonto = this.hauptbuch.suchen(buchungssatz.getSollkonto());
            final Saldo saldo = sollkonto.buchen(buchungssatz);

            final SaldoWurdeGeaendert ereignis = ImmutableSaldoWurdeGeaendert.builder()
                    .kontoname(buchungssatz.getSollkonto().toString())
                    .neuerSaldo(saldo)
                    .build();

            this.apply(ereignis);

            final Kontoname habenkonto = buchungssatz.getHabenkonto();
            final Saldo habenkontosaldo = this.hauptbuch.suchen(habenkonto).buchen(buchungssatz);
            final SaldoWurdeGeaendert habenkontoereignis = ImmutableSaldoWurdeGeaendert.builder()
                    .kontoname(buchungssatz.getHabenkonto().toString())
                    .neuerSaldo(habenkontosaldo)
                    .build();

            this.apply(habenkontoereignis);
        }
        else
        {
            this.apply(this.buchungAblehnen(buchungssatz));
        }
    }

    private BuchungWurdeAbgelehnt buchungAblehnen(final Buchungssatz buchungssatz)
    {
        final String grund = this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(
                buchungssatz.getSollkonto(),
                buchungssatz.getHabenkonto());

        return ImmutableBuchungWurdeAbgelehnt.builder().grund(grund).build();
    }

    @CommandHandler
    public void ausgabeBuchen(final ImmutableBucheAusgabe befehel)
    {
        final Buchungssatz buchungssatz = new Buchungssatz(
                Kontoname.of(befehel.sollkonto()),
                Kontoname.of(befehel.habenkonto()),
                befehel.waehrungsbetrag());

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz))
        {
            if (this.hauptbuch.kannAusgabeGebuchtWerden(buchungssatz))
            {
                this.buchungssatzHinzufügen(buchungssatz);
            }
            else
            {
                this.apply(ImmutableBuchungWurdeAbgelehnt.builder()
                                   .grund("Ausgaben können nicht auf Ertragskonten gebucht " + "werden.")
                                   .build());
            }
        }
        else
        {
            this.apply(ImmutableBuchungWurdeAbgelehnt.builder()
                               .grund(this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(
                                       Kontoname.of(befehel.sollkonto()),
                                       Kontoname.of(befehel.habenkonto())))
                               .build());
        }
    }

    @CommandHandler
    public void tilgungBuchen(final ImmutableBucheTilgung befehl)
    {
        final Buchungssatz buchungssatz = new Buchungssatz(
                Kontoname.of(befehl.sollkonto()),
                Kontoname.of(befehl.habenkonto()),
                befehl.waehrungsbetrag());

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz))
        {
            if (this.hauptbuch.kannTilgungGebuchtWerden(buchungssatz))
            {
                this.buchungssatzHinzufügen(buchungssatz);
            }
            else
            {
                this.apply(ImmutableBuchungWurdeAbgelehnt.builder()
                                   .grund("Tilgung kann nicht auf Konto gebucht werden.")
                                   .build());
            }
        }
        else
        {

            final String grund = this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(
                    Kontoname.of(befehl.sollkonto()),
                    Kontoname.of(befehl.habenkonto()));

            this.apply(ImmutableBuchungWurdeAbgelehnt.builder().grund(grund).build());
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("identitaetsmerkmal", this.id).toString();
    }
}
