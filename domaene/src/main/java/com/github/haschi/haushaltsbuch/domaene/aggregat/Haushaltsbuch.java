package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.api.BuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.api.Buchungsregel;
import com.github.haschi.haushaltsbuch.api.Buchungssatz;
import com.github.haschi.haushaltsbuch.api.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableBuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.api.ImmutableBuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.api.ImmutableHauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableKontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableKontoWurdeNichtAngelegt;
import com.github.haschi.haushaltsbuch.api.ImmutableSaldoWurdeGeändert;
import com.github.haschi.haushaltsbuch.api.JournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.Konto;
import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.Kontobezeichnung;
import com.github.haschi.haushaltsbuch.api.Saldo;
import com.github.haschi.haushaltsbuch.api.SaldoWurdeGeändert;
import com.github.haschi.haushaltsbuch.api.SollHabenSaldo;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheAnfangsbestand;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheAusgabe;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheEinnahme;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheTilgung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoAn;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoMitAnfangsbestandAn;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

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

    @SuppressWarnings("unused")
    protected Haushaltsbuch()
    {
        super();
    }

    @CommandHandler
    public Haushaltsbuch(final ImmutableBeginneHaushaltsbuchführung befehl)
    {
        super();

        this.apply(ImmutableHaushaltsbuchAngelegt.of(befehl.id()));
        this.apply(ImmutableJournalWurdeAngelegt.builder().aktuelleHaushaltsbuchId(befehl.id()).build());
        this.apply(ImmutableHauptbuchWurdeAngelegt.builder().haushaltsbuchId(befehl.id()).build());

        this.apply(ImmutableKontoWurdeAngelegt.builder()
                           .kontoart(Kontoart.Aktiv)
                           .kontobezeichnung(Konto.ANFANGSBESTAND.getName().toString())
                           .build());
    }

    @CommandHandler
    public void einnahmeBuchen(final ImmutableBucheEinnahme befehl)
    {
        this.buchungssatzHinzufügen(new Buchungssatz(
                Kontobezeichnung.of(befehl.sollkonto()),
                Kontobezeichnung.of(befehl.habenkonto()),
                befehl.geldbetrag()));
    }

    // Hauptbuch -- Alle Methoden zum Hauptbuch

    @CommandHandler
    public void neuesKontoHinzufügen(final ImmutableLegeKontoAn befehl)
    {
        if (this.hauptbuch.istKontoVorhanden(Kontobezeichnung.of(befehl.kontobezeichnung())))
        {
            this.apply(ImmutableKontoWurdeNichtAngelegt.builder()
                               .kontobezeichnung(befehl.kontobezeichnung())
                               .kontoart(befehl.kontoart())
                               .build());
        }
        else
        {
            this.apply(ImmutableKontoWurdeAngelegt.builder()
                               .kontobezeichnung(befehl.kontobezeichnung())
                               .kontoart(befehl.kontoart())
                               .build());

            this.apply(ImmutableSaldoWurdeGeändert.builder()
                               .kontobezeichnung(befehl.kontobezeichnung())
                               .neuerSaldo(new SollHabenSaldo())
                               .build());
        }
    }

    @CommandHandler
    public void kontoMitAnfangsbestandAnlegen(final ImmutableLegeKontoMitAnfangsbestandAn befehl)
    {
        if (this.hauptbuch.istKontoVorhanden(Kontobezeichnung.of(befehl.kontobezeichnung())))
        {
            this.apply(ImmutableKontoWurdeNichtAngelegt.builder()
                               .kontobezeichnung(befehl.kontobezeichnung())
                               .kontoart(befehl.kontoart())
                               .build());
        }
        else
        {
            this.apply(ImmutableKontoWurdeAngelegt.builder()
                               .kontobezeichnung(befehl.kontobezeichnung())
                               .kontoart(befehl.kontoart())
                               .build());

            this.anfangsbestandBuchen(ImmutableBucheAnfangsbestand.builder()
                                              .haushaltsbuchId(befehl.haushaltsbuchId())
                                              .kontobezeichnung(befehl.kontobezeichnung())
                                              .geldbetrag(befehl.betrag())
                                              .build());
        }
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
        final Buchungsregel regel = fabrik.erzeugen(Kontobezeichnung.of(ereignis.kontobezeichnung()));

        final Konto konto = new Konto(Kontobezeichnung.of(ereignis.kontobezeichnung()), regel);

        this.hauptbuch.hinzufügen(konto);
    }

    @EventSourcingHandler
    public void falls(final SaldoWurdeGeändert saldoGeaendert)
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
        if (this.journal.istAnfangsbestandFürKontoVorhanden(this.hauptbuch.suchen(Kontobezeichnung.of(befehl.kontobezeichnung()))))
        {
            apply(ImmutableBuchungWurdeAbgelehnt.builder().grund(FEHLERMELDUNG).build());
        }
        else
        {
            final Konto konto = this.hauptbuch.suchen(Kontobezeichnung.of(befehl.kontobezeichnung()));
            final Buchungssatz buchungssatz = konto.buchungssatzFürAnfangsbestand(befehl.geldbetrag());

            this.buchungssatzHinzufügen(buchungssatz);
        }
    }

    private void buchungssatzHinzufügen(final Buchungssatz buchungssatz)
    {

        if (this.hauptbuch.sindAlleKontenVorhanden(buchungssatz))
        {
            this.apply(ImmutableBuchungWurdeAusgeführt.builder().buchungssatz(buchungssatz).build());

            final Konto sollkonto = this.hauptbuch.suchen(buchungssatz.getSollkonto());
            final Saldo saldo = sollkonto.buchen(buchungssatz);

            final SaldoWurdeGeändert ereignis = ImmutableSaldoWurdeGeändert.builder()
                    .kontobezeichnung(buchungssatz.getSollkonto().toString())
                    .neuerSaldo(saldo)
                    .build();

            this.apply(ereignis);

            final Kontobezeichnung habenkonto = buchungssatz.getHabenkonto();
            final Saldo habenkontosaldo = this.hauptbuch.suchen(habenkonto).buchen(buchungssatz);
            final SaldoWurdeGeändert habenkontoereignis = ImmutableSaldoWurdeGeändert.builder()
                    .kontobezeichnung(buchungssatz.getHabenkonto().toString())
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
    public void ausgabeBuchen(final ImmutableBucheAusgabe befehl)
    {
        final Buchungssatz buchungssatz = new Buchungssatz(
                Kontobezeichnung.of(befehl.sollkonto()),
                Kontobezeichnung.of(befehl.habenkonto()),
                befehl.geldbetrag());

        if (this.hauptbuch.sindAlleKontenVorhanden(buchungssatz))
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
                                       Kontobezeichnung.of(befehl.sollkonto()),
                                       Kontobezeichnung.of(befehl.habenkonto())))
                               .build());
        }
    }

    @CommandHandler
    public void tilgungBuchen(final ImmutableBucheTilgung befehl)
    {
        final Buchungssatz buchungssatz = new Buchungssatz(
                Kontobezeichnung.of(befehl.sollkonto()),
                Kontobezeichnung.of(befehl.habenkonto()),
                befehl.geldbetrag());

        if (this.hauptbuch.sindAlleKontenVorhanden(buchungssatz))
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
                    Kontobezeichnung.of(befehl.sollkonto()),
                    Kontobezeichnung.of(befehl.habenkonto()));

            this.apply(ImmutableBuchungWurdeAbgelehnt.builder().grund(grund).build());
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("identitaetsmerkmal", this.id).toString();
    }
}
