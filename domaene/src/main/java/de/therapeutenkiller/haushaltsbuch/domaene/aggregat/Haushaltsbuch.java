package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.api.ereignis.KontoWurdeNichtAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.CoverageIgnore;
import de.therapeutenkiller.haushaltsbuch.domaene.HabenkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.KontonameSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.SollkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.BuchungssatzHinzufügen;
import de.therapeutenkiller.haushaltsbuch.domaene.support.AggregateRoot;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Haushaltsbuchereignis;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Haushaltsbuchsnapshot;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Spezifikation;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@CoverageIgnore
public final class Haushaltsbuch extends AggregateRoot<UUID, Haushaltsbuch> { // NOPMD Klasse zu groß TODO

    private static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";

    private final Set<Konto> konten = new HashSet<>();
    private final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public int initialVersion;

    private Haushaltsbuch() {
        super(UUID.randomUUID());
    }

    public Haushaltsbuch(final Haushaltsbuchsnapshot snapshot) {
        super(snapshot);
        this.initialVersion = snapshot.version;
    }

    public Haushaltsbuch(final UUID uuid) {
        super(uuid);
        this.causes(new HaushaltsbuchWurdeAngelegt(uuid));
    }

    public Haushaltsbuch(final HaushaltsbuchWurdeAngelegt ereignis) {
        super(ereignis.haushaltsbuchId);
    }

    public Haushaltsbuchsnapshot getSnapshot() {
        final Haushaltsbuchsnapshot snapshot = new Haushaltsbuchsnapshot(getIdentität(), getVersion());
        snapshot.konten = ImmutableSet.copyOf(this.konten);
        snapshot.buchungssätze = ImmutableList.of(this.buchungssätze);

        return snapshot;
    }

    // bewirkt
    private void causes(final Haushaltsbuchereignis ereignis) {
        this.ereignisHinzufügen(ereignis);
        this.anwenden(ereignis, this);
    }

    // Hauptbuch -- Alle Methoden zum Hauptbuch

    public void neuesKontoHinzufügen(final Konto konto) {
        this.konten.add(konto);
    }

    public void neuesKontoHinzufügen(final String kontoname, final Kontoart kontoart) {
        if (this.istKontoVorhanden(kontoname)) {
            this.causes(new KontoWurdeNichtAngelegt(kontoname, kontoart));
        } else {
            this.causes(new KontoWurdeAngelegt(getIdentität(), kontoname, kontoart));
        }
    }

    @CoverageIgnore
    public Konto kontoSuchen(final String kontoname) {

        final KontonameSpezifikation kontonameSpezifikation = new KontonameSpezifikation(kontoname);

        return this.konten.stream()
                .filter(kontonameSpezifikation::istErfülltVon)
                .findFirst()
                .get();
    }

    private boolean istKontoVorhanden(final String konto) {
        final KontonameSpezifikation kontoname = new KontonameSpezifikation(konto);
        return this.konten.stream().anyMatch(kontoname::istErfülltVon);
    }

    public ImmutableCollection<Konto> getKonten() {
        return ImmutableList.copyOf(this.konten); // NOPMD LoD TODO
    }

    private boolean kannAusgabeGebuchtWerden(final Buchungssatz buchungssatz) {
        final Konto sollkonto = this.kontoSuchen(buchungssatz.getSollkonto());
        final Konto habenkonto = this.kontoSuchen(buchungssatz.getHabenkonto());

        return sollkonto.kannAusgabeBuchen(buchungssatz) // NOPMD LoD TODO
                && habenkonto.kannAusgabeBuchen(buchungssatz); // NOPMD LoD TODO
    }

    // Journal -- Alle Methoden fürs Journal

    private String fehlermeldungFürFehlendeKontenErzeugen(
            final String soll,
            final String haben) {

        if (!this.istKontoVorhanden(soll) && this.istKontoVorhanden(haben)) {
            return String.format("Das Konto %s existiert nicht.", soll);
        }

        if (this.istKontoVorhanden(soll) && !this.istKontoVorhanden(haben)) {
            return String.format("Das Konto %s existiert nicht.", haben);
        }

        if (!this.istKontoVorhanden(soll) && !this.istKontoVorhanden(haben)) {

            return String.format("Die Konten %s und %s existieren nicht.", soll, haben);
        }

        throw new IllegalArgumentException("Die Fehlermeldung kann nicht erzeugt werden, da kein Fehler vorliegt.");
    }

    private boolean sindAlleBuchungskontenVorhanden(final String sollkonto, final String habenkonto) {
        return this.istKontoVorhanden(habenkonto) && this.istKontoVorhanden(sollkonto);
    }

    private boolean sindAlleBuchungskontenVorhanden(final Buchungssatz buchungssatz) {
        return this.sindAlleBuchungskontenVorhanden(buchungssatz.getSollkonto(), buchungssatz.getHabenkonto());
    }

    public Saldo kontostandBerechnen(final String kontoname) {
        final Konto konto = this.kontoSuchen(kontoname);
        return this.kontostandBerechnen(konto);
    }

    @CoverageIgnore
    private Saldo kontostandBerechnen(final Konto konto) {

        final SollkontoSpezifikation sollkonto = new SollkontoSpezifikation(konto);
        final MonetaryAmount summerDerSollbuchungen = this.summeFür(sollkonto);

        final HabenkontoSpezifikation habenkonto = new HabenkontoSpezifikation(konto);
        final MonetaryAmount summerDerHabenbuchungen = this.summeFür(habenkonto);

        return this.saldieren(summerDerSollbuchungen, summerDerHabenbuchungen);
    }

    private Saldo saldieren(final MonetaryAmount summerDerSollbuchungen, final MonetaryAmount summerDerHabenbuchungen) {
        if (summerDerHabenbuchungen.isEqualTo(summerDerSollbuchungen)) {
            return new SollHabenSaldo(summerDerHabenbuchungen.subtract(summerDerSollbuchungen));
        }

        if (summerDerHabenbuchungen.isGreaterThan(summerDerSollbuchungen)) {
            return new Habensaldo(summerDerHabenbuchungen.subtract(summerDerSollbuchungen));
        }

        return new Sollsaldo(summerDerSollbuchungen.subtract(summerDerHabenbuchungen));
    }

    private MonetaryAmount summeFür(final Spezifikation<Buchungssatz> buchungssatzspezifikation) {
        return this.buchungssätze.stream()
                    .filter(buchungssatzspezifikation::istErfülltVon)
                    .map(Buchungssatz::getWährungsbetrag)
                    .reduce(MonetaryFunctions.sum())
                    .orElse(Money.of(0, Monetary.getCurrency(Locale.GERMANY)));
    }

    public void neueBuchungHinzufügen(
            final String sollkonto,
            final String habenkonto,
            final MonetaryAmount betrag) {
        this.buchungssätze.add(new Buchungssatz(sollkonto, habenkonto, betrag));
    }

    public boolean istAnfangsbestandFürKontoVorhanden(final String konto) {
        return this.buchungssätze.stream().anyMatch(buchungssatz -> buchungssatz.istAnfangsbestandFür(konto));
    }

    public void falls(final HaushaltsbuchWurdeAngelegt haushaltsbuchWurdeAngelegt) {
        assert this.getIdentität().equals(haushaltsbuchWurdeAngelegt.haushaltsbuchId);
        this.konten.add(Konto.ANFANGSBESTAND);
    }

    public void falls(final KontoWurdeAngelegt kontoWurdeAngelegt) {
        final Buchungsregel regel = Buchungsregelfabrik.erzeugen(
                kontoWurdeAngelegt.kontoart,
                kontoWurdeAngelegt.kontoname);

        final Konto konto = new Konto(kontoWurdeAngelegt.kontoname, regel);
        this.neuesKontoHinzufügen(konto);
    }

    public void falls(final KontoWurdeNichtAngelegt kontoWurdeNichtAngelegt) {
        // Do Nothing
    }

    public void falls(final BuchungWurdeAbgelehnt buchungWurdeAbgelehnt) {
        // Nichts tun.
    }

    public void falls(final BuchungWurdeAusgeführt buchungWurdeAusgeführt) {

        this.neueBuchungHinzufügen(
                buchungWurdeAusgeführt.soll,
                buchungWurdeAusgeführt.haben,
                buchungWurdeAusgeführt.betrag);
    }

    public void anfangsbestandBuchen(
            final String kontoname,
            final MonetaryAmount betrag,
            final BuchungssatzHinzufügen buchungssatzHinzufügen) {
        if (this.istAnfangsbestandFürKontoVorhanden(kontoname)) {
            this.causes(new BuchungWurdeAbgelehnt(FEHLERMELDUNG));
        } else {
            buchungssatzHinzufügen.ausführen(
                    getIdentität(),
                    kontoname,
                    Konto.ANFANGSBESTAND.getBezeichnung(), // NOPMD LoD TODO
                    betrag);
        }
    }

    public void buchungssatzHinzufügen(final String sollkonto, final String habenkonto, final MonetaryAmount betrag) {
        if (this.sindAlleBuchungskontenVorhanden(sollkonto, habenkonto)) {
            this.causes(new BuchungWurdeAusgeführt(sollkonto, habenkonto, betrag));
        } else {
            final String fehlermeldung = this.fehlermeldungFürFehlendeKontenErzeugen(
                    sollkonto,
                    habenkonto);
            this.causes(new BuchungWurdeAbgelehnt(fehlermeldung));
        }
    }

    public void ausgabeBuchen(final String sollkonto, final String habenkonto, final MonetaryAmount betrag) {
        final Buchungssatz buchungssatz = new Buchungssatz(sollkonto, habenkonto, betrag);

        if (this.sindAlleBuchungskontenVorhanden(buchungssatz)) {
            if (this.kannAusgabeGebuchtWerden(buchungssatz)) {
                this.causes(new BuchungWurdeAusgeführt(sollkonto, habenkonto, betrag));
            } else {
                this.causes(new BuchungWurdeAbgelehnt("Ausgaben können nicht auf Ertragskonten gebucht werden."));
            }
        } else {
            this.causes(new BuchungWurdeAbgelehnt(
                            this.fehlermeldungFürFehlendeKontenErzeugen(sollkonto, habenkonto)));
        }
    }
}
