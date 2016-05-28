package com.github.haschi.haushaltsbuch.domaene.aggregat.ereignis;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.dominium.modell.EreignisZiel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignisziel;
import com.github.haschi.haushaltsbuch.domaene.aggregat.HaushaltsbuchEreignis;

import java.util.UUID;

@ValueObject
public final class HauptbuchWurdeAngelegt implements HaushaltsbuchEreignis  {

    private static final long serialVersionUID = -1916694080894642122L;
    private final UUID haushaltsbuchId;

    protected HauptbuchWurdeAngelegt() {
        super();
        this.haushaltsbuchId = null;
    }

    public HauptbuchWurdeAngelegt(final UUID haushaltsbuchId) {
        super();
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void anwendenAuf(final HaushaltsbuchEreignisziel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    @Override
    public void anwendenAuf(final EreignisZiel<HaushaltsbuchEreignisziel> ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
