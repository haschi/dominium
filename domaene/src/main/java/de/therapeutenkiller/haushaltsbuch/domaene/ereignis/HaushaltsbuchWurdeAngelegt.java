package de.therapeutenkiller.haushaltsbuch.domaene.ereignis;

import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;

public final class HaushaltsbuchWurdeAngelegt  {
    public final Haushaltsbuch haushaltsbuch;

    public HaushaltsbuchWurdeAngelegt(final Haushaltsbuch haushaltsbuch) {
        super();
        this.haushaltsbuch = haushaltsbuch;
    }
}
