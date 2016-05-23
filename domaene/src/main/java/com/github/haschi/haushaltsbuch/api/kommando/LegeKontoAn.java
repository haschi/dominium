package com.github.haschi.haushaltsbuch.api.kommando;

import com.github.haschi.coding.aspekte.ValueObject;
import com.github.haschi.haushaltsbuch.api.Kontoart;

import java.util.UUID;

@ValueObject
public class LegeKontoAn {
    public final UUID haushaltsbuchId;
    public final String kontoname;
    public final Kontoart kontoart;

    public LegeKontoAn(final UUID haushaltsbuchId, final String kontoname, final Kontoart kontoart) {

        super();

        this.haushaltsbuchId = haushaltsbuchId;
        this.kontoname = kontoname;
        this.kontoart = kontoart;
    }
}
