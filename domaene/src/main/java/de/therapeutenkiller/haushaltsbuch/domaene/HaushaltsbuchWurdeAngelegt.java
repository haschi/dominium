package de.therapeutenkiller.haushaltsbuch.domaene;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.support.Domänenereignis;

public class HaushaltsbuchWurdeAngelegt extends Domänenereignis {
    public Haushaltsbuch haushaltsbuch;

    public HaushaltsbuchWurdeAngelegt(final Haushaltsbuch haushaltsbuch) {
        super();
        this.haushaltsbuch = haushaltsbuch;
    }
}
