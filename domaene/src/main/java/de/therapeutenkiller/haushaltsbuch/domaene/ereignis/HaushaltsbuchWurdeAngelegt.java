package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt;

public final class HaushaltsbuchWurdeAngelegt extends Wertobjekt {

    // TODO Das Ereignis sollte nicht das Haushaltsbuch, sondern dessen ID enthalten.
    public final Haushaltsbuch haushaltsbuch;

    public HaushaltsbuchWurdeAngelegt(final Haushaltsbuch haushaltsbuch) {
        super();
        this.haushaltsbuch = haushaltsbuch;
    }
}
