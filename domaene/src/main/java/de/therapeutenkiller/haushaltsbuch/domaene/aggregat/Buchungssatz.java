package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.dominium.aggregat.Wertobjekt;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class Buchungssatz extends Wertobjekt {

    private final String sollkonto;
    private final String habenkonto;
    private final MonetaryAmount währungsbetrag;

    public Buchungssatz(final String sollkonto, final String habenkonto, final MonetaryAmount währungsbetrag) {
        super();

        if (währungsbetrag.isNegative()) {
            throw new IllegalArgumentException("Buchungssätze dürfen keine negativen Beträge besitzen.");
        }

        this.sollkonto = sollkonto;
        this.habenkonto = habenkonto;
        this.währungsbetrag = währungsbetrag;
    }

    public final boolean hatSollkonto(final String konto) {
        return this.sollkonto.equals(konto);
    }

    public final boolean hatHabenkonto(final String konto) {
        return this.habenkonto.equals(konto);
    }

    public final String getSollkonto() {
        return this.sollkonto;
    }

    public final String getHabenkonto() {
        return this.habenkonto;
    }

    public final MonetaryAmount getWährungsbetrag() {
        return this.währungsbetrag;
    }

    public final boolean istAnfangsbestandFür(final String konto) {
        return this.habenkonto.equals(konto)
                && this.sollkonto.equals(Konto.ANFANGSBESTAND.getBezeichnung()) // NOPMD LoD TODO
                || this.habenkonto.equals(Konto.ANFANGSBESTAND.getBezeichnung()) // NOPMD LoD TODO
                && this.sollkonto.equals(konto);
    }

    @Override
    public final String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.währungsbetrag); // NOPMD LoD TODO

        return String.format("%s (%s) an %s (%s)", // NOPMD LoD TODO
                this.sollkonto,
                betrag,
                this.habenkonto,
                betrag);
    }
}
