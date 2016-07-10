package com.github.haschi.haushaltsbuch.domaene.aggregat;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.BucheTilgung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheAnfangsbestand;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheAusgabe;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheEinnahme;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoAn;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoMitAnfangsbestandAn;
import com.github.haschi.haushaltsbuch.domaene.HabenkontoSpezifikation;
import com.github.haschi.haushaltsbuch.domaene.SollkontoSpezifikation;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableBuchungWurdeAbgelehnt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableBuchungWurdeAusgeführt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableHaushaltsbuchAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableKontoWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.ImmutableKontoWurdeNichtAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import javax.money.MonetaryAmount;
import java.util.UUID;

public final class Haushaltsbuch extends AbstractAnnotatedAggregateRoot<UUID> {

    private static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";
    private static final long serialVersionUID = -4864886584911078356L;

    private final Journal journal;
    private final Hauptbuch hauptbuch;

    @AggregateIdentifier
    private UUID id;

    protected Haushaltsbuch() {
        super();
        this.journal = new Journal();
        this.hauptbuch = new Hauptbuch();
    }

    @CommandHandler
    public Haushaltsbuch(final ImmutableBeginneHaushaltsbuchfuehrung befehl) {
        super();

        this.hauptbuch = new Hauptbuch();
        this.journal = new Journal();

        this.apply(ImmutableHaushaltsbuchAngelegt.of(befehl.id()));

        this.apply(ImmutableKontoWurdeAngelegt.builder()
            .kontoart(Kontoart.Aktiv)
            .kontoname(Konto.ANFANGSBESTAND.getBezeichnung())
            .build());
    }

    @CommandHandler
    public void on(final ImmutableBucheEinnahme befehl) {
        this.buchungssatzHinzufügen(new Buchungssatz(
            befehl.sollkonto(),
            befehl.habenkonto(),
            befehl.waehrungsbetrag()));
    }

    // ???
    public ImmutableSet<Konto> getKonten() {
        return this.hauptbuch.getKonten();
    }

    // Hauptbuch -- Alle Methoden zum Hauptbuch

    @CommandHandler
    public void neuesKontoHinzufügen(final ImmutableLegeKontoAn befehl) {
        if (this.hauptbuch.istKontoVorhanden(befehl.kontoname())) {
            this.apply(ImmutableKontoWurdeNichtAngelegt.builder()
                .kontoname(befehl.kontoname())
                .kontoart(befehl.kontoart())
                .build());

        } else {
            this.apply(ImmutableKontoWurdeAngelegt.builder()
                .kontoname(befehl.kontoname())
                .kontoart(befehl.kontoart())
                .build());
        }
    }

    @CommandHandler
    public void kontoMitAnfangsbestandAnlegen(final ImmutableLegeKontoMitAnfangsbestandAn befehl) {
        if (this.hauptbuch.istKontoVorhanden(befehl.kontoname())) {
            this.apply(ImmutableKontoWurdeNichtAngelegt.builder()
                .kontoname(befehl.kontoname())
                .kontoart(befehl.kontoart())
                .build());

            this.anfangsbestandBuchen(ImmutableBucheAnfangsbestand.builder()
                .haushaltsbuchId(befehl.haushaltsbuchId())
                .kontoname(befehl.kontoname())
                .waehrungsbetrag(befehl.betrag())
                .build());
        } else {
            this.apply(ImmutableKontoWurdeAngelegt.builder()
                .kontoname(befehl.kontoname())
                .kontoart(befehl.kontoart())
                .build());
        }
    }

    // ???
    public Saldo kontostandBerechnen(final String kontoname) {
        final Konto konto = this.hauptbuch.suchen(kontoname);
        return this.kontostandBerechnen(konto);
    }

    private Saldo kontostandBerechnen(final Konto konto) {

        final SollkontoSpezifikation sollkonto = new SollkontoSpezifikation(konto);
        final MonetaryAmount summerDerSollBuchungen = this.journal.summeFür(sollkonto);

        final HabenkontoSpezifikation habenkonto = new HabenkontoSpezifikation(konto);
        final MonetaryAmount summerDerHabenBuchungen = this.journal.summeFür(habenkonto);

        return this.saldieren(summerDerSollBuchungen, summerDerHabenBuchungen);
    }

    private Saldo saldieren(final MonetaryAmount summerDerSollBuchungen, final MonetaryAmount summerDerHabenBuchungen) {
        if (summerDerHabenBuchungen.isEqualTo(summerDerSollBuchungen)) {
            return new SollHabenSaldo(summerDerHabenBuchungen.subtract(summerDerSollBuchungen));
        }

        if (summerDerHabenBuchungen.isGreaterThan(summerDerSollBuchungen)) {
            return new Habensaldo(summerDerHabenBuchungen.subtract(summerDerSollBuchungen));
        }

        return new Sollsaldo(summerDerSollBuchungen.subtract(summerDerHabenBuchungen));
    }

    @EventSourcingHandler
    private void falls(final ImmutableHaushaltsbuchAngelegt ereignis) {
        this.id = ereignis.id();
    }

    @EventSourcingHandler
    public void falls(final ImmutableKontoWurdeAngelegt ereignis) {
        final BuchungsregelFabrik fabrik = new BuchungsregelFabrik(ereignis.kontoart());
        final Buchungsregel regel = fabrik.erzeugen(ereignis.kontoname());
        final Konto konto = new Konto(ereignis.kontoname(), regel);

        this.hauptbuch.hinzufügen(konto);
    }

    public void falls(final KontoWurdeNichtAngelegt kontoWurdeNichtAngelegt) {
        // nicht tun
    }

    public void falls(final BuchungWurdeAbgelehnt buchungWurdeAbgelehnt) {
        // Nichts tun
    }

    @EventSourcingHandler
    public void falls(final BuchungWurdeAusgeführt ereignis) {
        this.journal.buchungssatzHinzufügen(ereignis.buchungssatz());
    }

    public void falls(final HauptbuchWurdeAngelegt hauptbuchWurdeAngelegt) {
        // vorläufig nicht tun
    }

    public void falls(final JournalWurdeAngelegt journalWurdeAngelegt) {
        // vorläufig nichts tun
    }

    @CommandHandler
    public void anfangsbestandBuchen(final ImmutableBucheAnfangsbestand befehl) {
        if (this.journal.istAnfangsbestandFürKontoVorhanden(befehl.kontoname())) {
            apply(ImmutableBuchungWurdeAbgelehnt.builder().grund(FEHLERMELDUNG).build());
        } else {
            final Konto konto = this.hauptbuch.suchen(befehl.kontoname());
            final Buchungssatz buchungssatz = konto.buchungssatzFürAnfangsbestand(befehl.waehrungsbetrag());

            this.buchungssatzHinzufügen(buchungssatz);
        }
    }

    public void buchungssatzHinzufügen(final Buchungssatz buchungssatz) {

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz)) {
            this.apply(ImmutableBuchungWurdeAusgeführt.builder().buchungssatz(buchungssatz).build());
        } else {
            this.apply(this.buchungAblehnen(buchungssatz));
        }
    }

    private BuchungWurdeAbgelehnt buchungAblehnen(final Buchungssatz buchungssatz) {
        final String grund = this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(
            buchungssatz.getSollkonto(),
            buchungssatz.getHabenkonto());

        return ImmutableBuchungWurdeAbgelehnt.builder().grund(grund).build();
    }

    @CommandHandler
    public void ausgabeBuchen(final ImmutableBucheAusgabe befehel) {
        final Buchungssatz buchungssatz = new Buchungssatz(
            befehel.sollkonto(),
            befehel.habenkonto(),
            befehel.waehrungsbetrag());

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz)) {
            if (this.hauptbuch.kannAusgabeGebuchtWerden(buchungssatz)) {
                this.apply(ImmutableBuchungWurdeAusgeführt.builder()
                    .buchungssatz(buchungssatz)
                    .build());
            } else {
                this.apply(ImmutableBuchungWurdeAbgelehnt.builder()
                    .grund("Ausgaben können nicht auf Ertragskonten gebucht werden.")
                    .build());
            }
        } else {
            this.apply(ImmutableBuchungWurdeAbgelehnt.builder()
                .grund(this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(befehel.sollkonto(), befehel.habenkonto()))
                .build());
        }
    }

    @CommandHandler
    public void tilgungBuchen(final BucheTilgung befehl) {
        final Buchungssatz buchungssatz = new Buchungssatz(
            befehl.sollkonto(),
            befehl.habenkonto(),
            befehl.waehrungsbetrag());

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz)) {
            if (this.hauptbuch.kannTilgungGebuchtWerden(buchungssatz)) {
                this.apply(ImmutableBuchungWurdeAusgeführt.builder()
                    .buchungssatz(buchungssatz)
                    .build());
            } else {
                this.apply(ImmutableBuchungWurdeAbgelehnt.builder()
                    .grund("Tilgung kann nicht auf Konto gebucht werden.")
                    .build());
            }
        } else {

            final String grund = this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(
                befehl.sollkonto(),
                befehl.habenkonto());

            this.apply(ImmutableBuchungWurdeAbgelehnt.builder()
                .grund(grund)
                .build());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identitätsmerkmal", this.id)
                .toString();
    }
}
