package com.github.haschi.haushaltsbuch.domaene;

import com.github.haschi.haushaltsbuch.domaene.testsupport.DieWelt;
import com.github.haschi.haushaltsbuch.domaene.testsupport.MoneyConverter;
import cucumber.api.Transform;
import cucumber.api.java.de.Wenn;
import com.github.haschi.haushaltsbuch.api.kommando.BucheEinnahme;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.money.MonetaryAmount;

public final class EinnahmeBuchenSteps {

    @Inject
    private DieWelt kontext;

    @Inject
    private Event<BucheEinnahme> einnahmeBuchen;

    @Wenn("^ich meine Einnahme von (-?\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void ich_meine_einnahme_per_an_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto) {

        final BucheEinnahme kommando = new BucheEinnahme(
                this.kontext.getAktuelleHaushaltsbuchId(),
                sollkonto,
                habenkonto,
                währungsbetrag);

        this.einnahmeBuchen.fire(kommando);
    }
}
