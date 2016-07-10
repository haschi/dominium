package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.api.kommando.BucheAnfangsbestand;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheAnfangsbestand;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Habensaldo;
import com.github.haschi.haushaltsbuch.domaene.aggregat.Sollsaldo;
import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.HabensaldoConverter;
import com.github.haschi.haushaltsbuch.domaene.testsupport.MoneyConverter;
import com.github.haschi.haushaltsbuch.domaene.testsupport.SollsaldoConverter;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import javax.money.MonetaryAmount;

public final class AnfangsbestandBuchenSteps {

    @Inject
    private DieWelt welt;

    @Inject
    private CommandGateway commandGateway;

    @Wenn("^ich auf das Konto \"([^\"]*)\" (?:den Anfangsbestand von) (-?\\d+,\\d{2} [A-Z]{3}) buche$")
    public void wenn_ich_auf_das_Konto_den_Anfangsbestand_buche(
            final String konto,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {

        throw new PendingException();
    }

    @Dann("^(?:werde ich|ich werde) auf dem Konto \"([^\"]*)\" ein Sollsaldo von (-?\\d+,\\d{2} [A-Z]{3}) haben$")
    public void dann_werde_ich_auf_dem_Konto_ein_Sollsaldo_haben(
            final String konto,
            @Transform(SollsaldoConverter.class) final Sollsaldo erwarteterSaldo) {
        throw new PendingException();
    }

    @Dann("^(?:werde ich|ich werde) auf dem Konto \"([^\"]*)\" ein Habensaldo von (-?\\d+,\\d{2} [A-Z]{3}) haben$")
    public void dann_werde_ich_auf_dem_Konto_ein_Habensaldo_haben(
            final String konto,
            @Transform(HabensaldoConverter.class) final Habensaldo erwarteterSaldo) {
        throw new PendingException();
    }

    @Und("^ich habe auf das Konto \"([^\"]*)\" den Anfangsbestand von (-?\\d+,\\d{2} [A-Z]{3}) gebucht$")
    public void angenommen_ich_habe_auf_das_Konto_den_Anfangsbestand_gebucht(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {


        final BucheAnfangsbestand befehl = ImmutableBucheAnfangsbestand.builder()
            .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
            .kontoname(kontoname)
            .waehrungsbetrag(währungsbetrag)
            .build();

        commandGateway.sendAndWait(befehl);
    }

    @Wenn("^ich weitere (-?\\d+,\\d{2} [A-Z]{3}) auf das Konto \"([^\"]*)\" als Anfangsbestand buche$")
    public void wenn_ich_weitere_Euro_auf_das_Konto_als_Anfangsbestand_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String kontoname) throws Throwable {

        this.commandGateway.sendAndWait(ImmutableBucheAnfangsbestand.builder()
            .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
            .kontoname(kontoname)
            .waehrungsbetrag(währungsbetrag)
            .build());
    }
}
