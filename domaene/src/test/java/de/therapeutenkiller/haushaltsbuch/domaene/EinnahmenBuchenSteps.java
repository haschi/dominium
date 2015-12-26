package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.EinnahmeBuchen;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

@Singleton
public final class EinnahmenBuchenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final EinnahmeBuchen einnahmeBuchen;

    @Inject
    public EinnahmenBuchenSteps(
            final HaushaltsbuchführungBeginnenKontext kontext,
            final EinnahmeBuchen einnahmeBuchen) {
        this.kontext = kontext;

        this.einnahmeBuchen = einnahmeBuchen;
    }

    @Wenn("^ich meine Einnahmen von (-{0,1}\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void ich_meine_einnahmen_per_an_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto) throws Throwable {

        this.einnahmeBuchen.ausführen(this.kontext.aktuellesHaushaltsbuch(), sollkonto, habenkonto, währungsbetrag);
    }
}
