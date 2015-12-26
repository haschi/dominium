package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Und;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Buchungssatz;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.EinnahmeBuchen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.BuchungssatzWurdeErstellt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class EinnahmeBuchenSteps {

    private final HaushaltsbuchführungBeginnenKontext kontext;
    private final EinnahmeBuchen einnahmeBuchen;
    private BuchungssatzWurdeErstellt buchungssatzWurdeAngelegt;

    @Inject
    public EinnahmeBuchenSteps(
            final HaushaltsbuchführungBeginnenKontext kontext,
            final EinnahmeBuchen einnahmeBuchen) {
        this.kontext = kontext;
        this.einnahmeBuchen = einnahmeBuchen;
    }

    public void buchungssatzWurdeAngelegtHandler(@Observes final BuchungssatzWurdeErstellt buchungssatzWurdeAngelegt) {
        this.buchungssatzWurdeAngelegt = buchungssatzWurdeAngelegt;
    }

    @Wenn("^ich meine Einnahme von (-{0,1}\\d+,\\d{2} [A-Z]{3}) per \"([^\"]*)\" an \"([^\"]*)\" buche$")
    public void ich_meine_einnahmen_per_an_buche(
            @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag,
            final String sollkonto,
            final String habenkonto) {

        this.einnahmeBuchen.ausführen(this.kontext.aktuellesHaushaltsbuch(), sollkonto, habenkonto, währungsbetrag);
    }

    @Und("^ich werde den Buchungssatz \"([^\"]*)\" angelegt haben$")
    public void derBuchungssatzWurdeAngelegt(final String erwarteterBuchungssatz) {
        final Buchungssatz aktuellerBuchungssatz = this.buchungssatzWurdeAngelegt.getBuchungssatz();

        assertThat(aktuellerBuchungssatz.toString()).isEqualTo(erwarteterBuchungssatz); // NOPMD LoD TODO
    }
}
