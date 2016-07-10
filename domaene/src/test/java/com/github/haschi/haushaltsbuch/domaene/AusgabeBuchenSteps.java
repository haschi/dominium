package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.domaene.aggregat.Habensaldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Saldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.Kontostand;
import com.github.haschi.haushaltsbuch.domaene.testsupport.MoneyConverter;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.util.List;

public final class AusgabeBuchenSteps {

    @Inject
    private DieWelt welt;

    @Wenn("^ich meine Ausgabe von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_ausgabe_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto)  {

        throw new PendingException();
    }

    @Wenn("^ich meine Tilgung von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void wenn_ich_meine_tilgung_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto) {
        throw new PendingException();
    }

    @Dann("^werde ich folgende Kontostände erhalten:$")
    public void dann_werde_ich_folgende_Kontostände_erhalten(final List<Kontostand> kontostände) {
        throw new PendingException();
    }

    private static Saldo saldoFürKonto(final Kontostand kontostand) {
        switch (kontostand.kontoart) {
            case Aktiv: return new Sollsaldo(kontostand.betrag);
            case Ertrag: return new Habensaldo(kontostand.betrag);
            case Aufwand: return new Sollsaldo(kontostand.betrag);
            case Passiv: return new Sollsaldo(kontostand.betrag);
            default: throw new IllegalArgumentException("kontostand");
        }
    }
}
