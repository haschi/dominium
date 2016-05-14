package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import de.therapeutenkiller.coding.annotation.Builder;
import de.therapeutenkiller.coding.aspekte.ValueObject;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

@Builder
@ValueObject
public final class Buchungssatz {

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

    public boolean hatSollkonto(final String konto) {
        return this.sollkonto.equals(konto);
    }

    public boolean hatHabenkonto(final String konto) {
        return this.habenkonto.equals(konto);
    }

    public String getSollkonto() {
        return this.sollkonto;
    }

    public String getHabenkonto() {
        return this.habenkonto;
    }

    public MonetaryAmount getWährungsbetrag() {
        return this.währungsbetrag;
    }

    public boolean istAnfangsbestandFür(final String konto) {
        return (this.habenkonto.equals(konto)
                && this.sollkonto.equals(Konto.ANFANGSBESTAND.getBezeichnung()))
                || (this.habenkonto.equals(Konto.ANFANGSBESTAND.getBezeichnung())
                && this.sollkonto.equals(konto));
    }

    @Override
    public String toString() {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final String betrag = format.format(this.währungsbetrag); // NOPMD LoD TODO

        return String.format("%s (%s) an %s (%s)", // NOPMD LoD TODO
                this.sollkonto,
                betrag,
                this.habenkonto,
                betrag);
    }
}
