package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.persistenz.EreignisstromNichtVorhanden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.HabenkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.KontonameSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.SollkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.anwendungsfall.BuchungssatzHinzufügen;
import de.therapeutenkiller.dominium.aggregat.Spezifikation;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public final class Haushaltsbuch extends Aggregatwurzel<Haushaltsbuch, UUID> { // NOPMD
    // Klasse zu groß TODO

    private static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";

    private final Set<Konto> konten = new HashSet<>();
    private final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public long initialVersion;

    public Haushaltsbuch(final HaushaltsbuchSchnappschuss snapshot) {
        super(snapshot);
        this.initialVersion = snapshot.version;
    }

    public Haushaltsbuch(final UUID uuid) {
        super(uuid);
    }

    @Override
    public HaushaltsbuchSchnappschuss schnappschussErstellen() {
        final HaushaltsbuchSchnappschuss schnappschuss = new HaushaltsbuchSchnappschuss(
                this.getIdentitätsmerkmal(),
                this.getVersion());

        schnappschuss.konten = ImmutableSet.copyOf(this.konten);
        schnappschuss.buchungssätze = ImmutableList.of(this.buchungssätze);

        return schnappschuss;
    }

    // Hauptbuch -- Alle Methoden zum Hauptbuch

    public void neuesKontoHinzufügen(final Konto konto) {
        this.konten.add(konto);
    }

    public void neuesKontoHinzufügen(final String kontoname, final Kontoart kontoart) {
        if (this.istKontoVorhanden(kontoname)) {
            this.bewirkt(new KontoWurdeNichtAngelegt(kontoname, kontoart));
        } else {
            this.bewirkt(new KontoWurdeAngelegt(kontoname, kontoart));
        }
    }

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

    private Saldo kontostandBerechnen(final Konto konto) {

        final SollkontoSpezifikation sollkonto = new SollkontoSpezifikation(konto);
        final MonetaryAmount summerDerSollBuchungen = this.summeFür(sollkonto);

        final HabenkontoSpezifikation habenkonto = new HabenkontoSpezifikation(konto);
        final MonetaryAmount summerDerHabenBuchungen = this.summeFür(habenkonto);

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

    private MonetaryAmount summeFür(final Spezifikation<Buchungssatz> buchungssatzSpezifikation) {
        return this.buchungssätze.stream()
                    .filter(buchungssatzSpezifikation::istErfülltVon)
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

    public void falls(final KontoWurdeAngelegt kontoWurdeAngelegt) {
        final Buchungsregel regel = BuchungsregelFabrik.erzeugen(
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
            final BuchungssatzHinzufügen buchungssatzHinzufügen)
            throws KonkurrierenderZugriff, EreignisstromNichtVorhanden {
        if (this.istAnfangsbestandFürKontoVorhanden(kontoname)) {
            this.bewirkt(new BuchungWurdeAbgelehnt(FEHLERMELDUNG));
        } else {
            buchungssatzHinzufügen.ausführen(
                    getIdentitätsmerkmal(),
                    kontoname,
                    Konto.ANFANGSBESTAND.getBezeichnung(), // NOPMD LoD TODO
                    betrag);
        }
    }

    public void buchungssatzHinzufügen(final String sollkonto, final String habenkonto, final MonetaryAmount betrag) {
        if (this.sindAlleBuchungskontenVorhanden(sollkonto, habenkonto)) {
            this.bewirkt(new BuchungWurdeAusgeführt(sollkonto, habenkonto, betrag));
        } else {
            final String fehlermeldung = this.fehlermeldungFürFehlendeKontenErzeugen(
                    sollkonto,
                    habenkonto);
            this.bewirkt(new BuchungWurdeAbgelehnt(fehlermeldung));
        }
    }

    public void ausgabeBuchen(final String sollkonto, final String habenkonto, final MonetaryAmount betrag) {
        final Buchungssatz buchungssatz = new Buchungssatz(sollkonto, habenkonto, betrag);

        if (this.sindAlleBuchungskontenVorhanden(buchungssatz)) {
            if (this.kannAusgabeGebuchtWerden(buchungssatz)) {
                this.bewirkt(new BuchungWurdeAusgeführt(sollkonto, habenkonto, betrag));
            } else {
                this.bewirkt(new BuchungWurdeAbgelehnt("Ausgaben können nicht auf Ertragskonten gebucht werden."));
            }
        } else {
            this.bewirkt(new BuchungWurdeAbgelehnt(
                            this.fehlermeldungFürFehlendeKontenErzeugen(sollkonto, habenkonto)));
        }
    }

    @Override
    protected Haushaltsbuch getSelf() {
        return this;
    }
}
