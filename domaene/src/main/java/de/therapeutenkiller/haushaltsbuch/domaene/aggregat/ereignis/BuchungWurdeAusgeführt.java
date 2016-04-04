package de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import org.apache.commons.lang3.StringUtils;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Locale;

@Entity
public final class BuchungWurdeAusgeführt extends HaushaltsbuchEreignis implements Serializable {

    private static final long serialVersionUID = 1L;

    public final String soll;
    public final String haben;
    protected final double betrag;

    protected BuchungWurdeAusgeführt() {
        this.soll = StringUtils.EMPTY;
        this.haben = StringUtils.EMPTY;
        this.betrag = 0;
    }

    public BuchungWurdeAusgeführt(final String soll, final String haben, final MonetaryAmount betrag) {

        super();

        this.soll = soll;
        this.haben = haben;
        this.betrag = betrag.getNumber().doubleValueExact();
    }

    public BuchungWurdeAusgeführt(final Buchungssatz buchungssatz) {
        this(buchungssatz.getSollkonto(), buchungssatz.getHabenkonto(), buchungssatz.getWährungsbetrag());
    }

    public MonetaryAmount getBetrag() {
        final MonetaryAmountFormat amountFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        return Money.of(this.betrag, "EUR");
    }

    public Buchungssatz getBuchungssatz() {
        return new Buchungssatz(this.soll, this.haben, this.getBetrag());
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
