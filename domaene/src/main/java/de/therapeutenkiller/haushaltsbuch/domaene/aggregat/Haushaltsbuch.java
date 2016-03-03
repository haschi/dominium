package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableSet;
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
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.money.MonetaryAmount;
import java.util.UUID;

public final class Haushaltsbuch extends Aggregatwurzel<Haushaltsbuch, UUID, HaushaltsbuchEreignisziel>
        implements HaushaltsbuchEreignisziel {

    private static final String FEHLERMELDUNG = "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden";
    private Journal journal = Journal.UNDEFINIERT;
    private Hauptbuch hauptbuch = Hauptbuch.UNDEFINIERT;

    public Haushaltsbuch(final HaushaltsbuchSchnappschuss snapshot) {
        super(snapshot);
    }

    public Haushaltsbuch(final UUID uuid) {
        super(uuid);
    }

    // ???
    public ImmutableSet<Konto> getKonten() {
        return this.hauptbuch.getKonten();
    }

    @Override
    public HaushaltsbuchSchnappschuss schnappschussErstellen() {
        final HaushaltsbuchSchnappschuss schnappschuss = new HaushaltsbuchSchnappschuss(
                this.getIdentitätsmerkmal(),
                this.getVersion());

        schnappschuss.konten = this.hauptbuch.getKonten();
        schnappschuss.buchungssätze = this.journal.getBuchungssätze();

        return schnappschuss;
    }

    // Hauptbuch -- Alle Methoden zum Hauptbuch

    // !!!
    public void neuesKontoHinzufügen(final String kontoname, final Kontoart kontoart) {
        if (this.hauptbuch.istKontoVorhanden(kontoname)) {
            this.bewirkt(new KontoWurdeNichtAngelegt(kontoname, kontoart));
        } else {
            this.bewirkt(new KontoWurdeAngelegt(kontoname, kontoart));
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

    // !!!
    @Override
    public void falls(final KontoWurdeAngelegt kontoWurdeAngelegt) {
        final Buchungsregel regel = BuchungsregelFabrik.erzeugen(
                kontoWurdeAngelegt.kontoart,
                kontoWurdeAngelegt.kontoname);

        final Konto konto = new Konto(kontoWurdeAngelegt.kontoname, regel);
        this.hauptbuch.hinzufügen(konto);
    }

    // !!!
    @Override
    public void falls(final KontoWurdeNichtAngelegt kontoWurdeNichtAngelegt) {
        // Do Nothing
    }

    // !!!
    @Override
    public void falls(final BuchungWurdeAbgelehnt buchungWurdeAbgelehnt) {
        // Nichts tun.
    }

    // !!!
    @Override
    public void falls(final BuchungWurdeAusgeführt buchungWurdeAusgeführt) {

        this.journal.buchungssatzHinzufügen(buchungWurdeAusgeführt.getBuchungssatz());
    }

    // !!!
    @Override
    public void falls(final HauptbuchWurdeAngelegt hauptbuchWurdeAngelegt) {
        this.hauptbuch = new Hauptbuch();
    }

    // !!!
    @Override
    public void falls(final JournalWurdeAngelegt journalWurdeAngelegt) {
        this.journal = new Journal();
    }

    // !!!
    public void anfangsbestandBuchen(
            final String kontoname,
            final MonetaryAmount betrag)
            throws KonkurrierenderZugriff, AggregatNichtGefunden {
        if (this.journal.istAnfangsbestandFürKontoVorhanden(kontoname)) {
            this.bewirkt(new BuchungWurdeAbgelehnt(FEHLERMELDUNG));
        } else {
            final Konto konto = this.hauptbuch.suchen(kontoname);

            final Buchungssatz buchungssatz = konto.buchungssatzFürAnfangsbestand(betrag);

            this.buchungssatzHinzufügen(
                    buchungssatz.getSollkonto(),
                    buchungssatz.getHabenkonto(),
                    buchungssatz.getWährungsbetrag());
        }
    }

    // !!! private und dann mit buchungssaz als parameter
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

    // !!! Parameter: besser Buchungssatz?
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

    public void tilgungBuchen(final String sollkonto, final String habenkonto, final MonetaryAmount währungsbetrag) {
        final Buchungssatz buchungssatz = new Buchungssatz(sollkonto, habenkonto, währungsbetrag);

        if (this.hauptbuch.sindAlleBuchungskontenVorhanden(buchungssatz)) {
            if (this.hauptbuch.kannTilgungGebuchtWerden(buchungssatz)) {
                this.bewirkt(new BuchungWurdeAusgeführt(sollkonto, habenkonto, währungsbetrag));
            } else {
                this.bewirkt(new BuchungWurdeAbgelehnt("Tilgung kann nicht auf Konto gebucht werden."));
            }
        } else {
            final String grund = this.hauptbuch.fehlermeldungFürFehlendeKontenErzeugen(sollkonto, habenkonto);
            this.bewirkt( new BuchungWurdeAbgelehnt(grund));
        }
    }

    @Override
    protected HaushaltsbuchEreignisziel getSelf() {
        return this;
    }

    // !!!
    public void hauptbuchAnlegen() {
        bewirkt(new HauptbuchWurdeAngelegt(this.getIdentitätsmerkmal()));
    }

    // !!!
    public void journalAnlegen() {
        bewirkt(new JournalWurdeAngelegt(this.getIdentitätsmerkmal()));
    }

    // ???
    public boolean istHauptbuchUndefiniert() {
        return this.hauptbuch == Hauptbuch.UNDEFINIERT;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identitätsmerkmal", this.getIdentitätsmerkmal())
                .toString();
    }
}
