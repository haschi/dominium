package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheEinnahme;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

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
