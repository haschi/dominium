package de.therapeutenkiller.haushaltsbuch.domaene.aggregat;

import com.google.common.collect.ImmutableList;
import de.therapeutenkiller.dominium.aggregat.Spezifikation;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public final class Journal2 {
    public static final Journal2 UNDEFINIERT = new Journal2();
    public final Set<Buchungssatz> buchungssätze = new HashSet<>();

    public Journal2() {
    }

    public boolean istAnfangsbestandFürKontoVorhanden(final String konto) {
        return this.buchungssätze.stream().anyMatch(buchungssatz -> buchungssatz.istAnfangsbestandFür(konto));
    }

    boolean buchungssatzHinzufügen(final Buchungssatz buchungssatz) {
        return this.buchungssätze.add(buchungssatz);
    }

    MonetaryAmount summeFür(final Spezifikation<Buchungssatz> buchungssatzSpezifikation) {
        return this.buchungssätze.stream()
                    .filter(buchungssatzSpezifikation::istErfülltVon)
                    .map(Buchungssatz::getWährungsbetrag)
                    .reduce(MonetaryFunctions.sum())
                    .orElse(Money.of(0, Monetary.getCurrency(Locale.GERMANY)));
    }

    ImmutableList<Set<Buchungssatz>> getBuchungssätze() {
        return ImmutableList.of(this.buchungssätze);
    }
}