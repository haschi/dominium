package de.therapeutenkiller.haushaltsbuch.domaene;

import cucumber.api.java.de.Angenommen;
import de.therapeutenkiller.haushaltsbuch.domaene.anwendungsfall.KontoAnlegen;
import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.HaushaltsbuchWurdeAngelegt;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

/**
 * Created by matthias on 18.12.15.
 */

@Singleton
public final class AnfangsbestandVerbuchenSteps {

    private final KontoAnlegen kontoAnlegen;
    private UUID aktuellesHaushaltsbuch;

    @Inject
    public AnfangsbestandVerbuchenSteps(final KontoAnlegen kontoAnlegen) {
        this.kontoAnlegen = kontoAnlegen;
        ;
    }

    public void haushaltsbuchWurdeAngelegt(@Observes final HaushaltsbuchWurdeAngelegt ereignis) {
        this.aktuellesHaushaltsbuch = ereignis.haushaltsbuch.getIdentität(); // NOPMD LoD TODO
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname) {
        this.kontoAnlegen.ausführen(this.aktuellesHaushaltsbuch, kontoname);
    }
}
