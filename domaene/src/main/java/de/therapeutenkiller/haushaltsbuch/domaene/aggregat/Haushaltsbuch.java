package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableList;
import de.therapeutenkiller.dominium.aggregat.Spezifikation;
import de.therapeutenkiller.dominium.modell.Aggregatwurzel;
import de.therapeutenkiller.dominium.persistenz.AggregatNichtGefunden;
import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.api.Kontoart;
import de.therapeutenkiller.haushaltsbuch.domaene.HabenkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.SollkontoSpezifikation;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAbgelehnt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.BuchungWurdeAusgeführt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.KontoWurdeNichtAngelegt;
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

    public Hauptbuch hauptbuch = Hauptbuch.UNDEFINIERT;
    private final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public long initialVersion;
    private Journal journal = Journal.LEER;

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

        schnappschuss.konten = this.hauptbuch.getKonten();
        schnappschuss.buchungssätze = ImmutableList.of(this.buchungssätze);

        return schnappschuss;
    }

    // Hauptbuch -- Alle Methoden zum Hauptbuch

    public void neuesKontoHinzufügen(final String kontoname, final Kontoart kontoart) {
        if (this.hauptbuch.istKontoVorhanden(kontoname)) {
            this.bewirkt(new KontoWurdeNichtAngelegt(kontoname, kontoart));
        } else {
            this.bewirkt(new KontoWurdeAngelegt(kontoname, kontoart));
        }
    }

    // Journal -- Alle Methoden fürs Journal

    public Saldo kontostandBerechnen(final String kontoname) {
        final Konto konto = this.hauptbuch.suchen(kontoname);
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

    public boolean istAnfangsbestandFürKontoVorhanden(final String konto) {
        return this.buchungssätze.stream().anyMatch(buchungssatz -> buchungssatz.istAnfangsbestandFür(konto));
    }

    public void falls(final KontoWurdeAngelegt kontoWurdeAngelegt) {
        final Buchungsregel regel = BuchungsregelFabrik.erzeugen(
                kontoWurdeAngelegt.kontoart,
                kontoWurdeAngelegt.kontoname);

        final Konto konto = new Konto(kontoWurdeAngelegt.kontoname, regel);
        this.hauptbuch.hinzufügen(konto);
    }

    public void falls(final KontoWurdeNichtAngelegt kontoWurdeNichtAngelegt) {
        // Do Nothing
    }

    public void falls(final BuchungWurdeAbgelehnt buchungWurdeAbgelehnt) {
        // Nichts tun.
    }

    public void falls(final BuchungWurdeAusgeführt buchungWurdeAusgeführt) {

        this.buchungssätze.add(buchungWurdeAusgeführt.getBuchungssatz());
    }

    public void falls(final HauptbuchWurdeAngelegt hauptbuchWurdeAngelegt) {
        this.hauptbuch = new Hauptbuch();
    }

    public void falls(final JournalWurdeAngelegt journalWurdeAngelegt) {
        this.journal = new Journal();
    }

    public void anfangsbestandBuchen(
            final String kontoname,
            final MonetaryAmount betrag)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {
        if (this.istAnfangsbestandFürKontoVorhanden(kontoname)) {
            this.bewirkt(new BuchungWurdeAbgelehnt(FEHLERMELDUNG));
        } else {
            this.buchungssatzHinzufügen(kontoname, Konto.ANFANGSBESTAND.getBezeichnung(), betrag);
        }
    }

    public void buchungssatzHinzufügen(final String sollkonto, final String habenkonto, final MonetaryAmount betrag) {
        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(sollkonto, habenkonto)) {
            this.bewirkt(new BuchungWurdeAusgeführt(sollkonto, habenkonto, betrag));
        } else {
            final String fehlermeldung = this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(
                    sollkonto,
                    habenkonto);

            this.bewirkt(new BuchungWurdeAbgelehnt(fehlermeldung));
        }
    }

    public void ausgabeBuchen(final String sollkonto, final String habenkonto, final MonetaryAmount betrag) {
        final Buchungssatz buchungssatz = new Buchungssatz(sollkonto, habenkonto, betrag);

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz)) {
            if (this.hauptbuch.kannAusgabeGebuchtWerden(buchungssatz)) {
                this.bewirkt(new BuchungWurdeAusgeführt(sollkonto, habenkonto, betrag));
            } else {
                this.bewirkt(new BuchungWurdeAbgelehnt("Ausgaben können nicht auf Ertragskonten gebucht werden."));
            }
        } else {
            this.bewirkt(new BuchungWurdeAbgelehnt(
                            this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(sollkonto, habenkonto)));
        }
    }

    @Override
    protected Haushaltsbuch getSelf() {
        return this;
    }

    public void hauptbuchAnlegen() {
        bewirkt(new HauptbuchWurdeAngelegt(this.getIdentitätsmerkmal()));
    }

    public void journalAnlegen() {
        bewirkt(new JournalWurdeAngelegt(this.getIdentitätsmerkmal()));
    }

    public boolean istHauptbuchUndefiniert() {
        return this.hauptbuch == Hauptbuch.UNDEFINIERT;
    }
}
