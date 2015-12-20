package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.Transform;
import cucumber.api.java.de.Angenommen;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.AnfangsbestandBuchen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.HaushaltsbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import java.util.UUID;

@Singleton
public final class AnfangsbestandVerbuchenSteps {

    private final KontoAnlegen kontoAnlegen;
    private UUID aktuellesHaushaltsbuch;
    private final AnfangsbestandBuchen anfangsbestandBuchen;

    @Inject
    public AnfangsbestandVerbuchenSteps(
            final KontoAnlegen kontoAnlegen,
            final AnfangsbestandBuchen anfangsbestandBuchen) {
        this.kontoAnlegen = kontoAnlegen;
        this.anfangsbestandBuchen = anfangsbestandBuchen;
    }

    public void haushaltsbuchWurdeAngelegt(@Observes final HaushaltsbuchWurdeAngelegt ereignis) {
        this.aktuellesHaushaltsbuch = ereignis.haushaltsbuch.getIdentität(); // NOPMD LoD TODO
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname) {
        this.kontoAnlegen.ausführen(this.aktuellesHaushaltsbuch, kontoname);
    }

    @Wenn("^ich auf das Konto \"([^\"]*)\" den Anfangsbestand von (-{0,1}\\d+,\\d{2} [A-Z]{3}) buche$")
    public void ichAufDasKontoDenAnfangsbestandVonEurBuche(
            final String kontoname,
            @Transform(MoneyConverter.class) final MonetaryAmount betrag) {
        this.anfangsbestandBuchen.ausführen(this.aktuellesHaushaltsbuch, kontoname, betrag);
    }
}
