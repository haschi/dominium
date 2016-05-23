package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoAn;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named
@RequestScoped
@SuppressWarnings("checkstyle:designforextension")
public class KontoAnlegen {

    private String id = "";

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    private String kontoname = "";

    public String getKontoname() {
        return this.kontoname;
    }

    public void setKontoname(final String kontoname) {
        this.kontoname = kontoname;
    }

    private Kontoart kontoart = Kontoart.Aktiv;

    public Kontoart getKontoart() {
        return this.kontoart;
    }

    public void setKontoart(final Kontoart kontoart) {
        this.kontoart = kontoart;
    }

    public Kontoart[] getKontoarten() {
        return Kontoart.values();
    }

    @Inject
    private Event<LegeKontoAn> legeKontoAnEvent;

    public String ausführen() {
        final LegeKontoAn befehl = new LegeKontoAn(UUID.fromString(this.id), this.kontoname, this.kontoart);
        this.legeKontoAnEvent.fire(befehl);

        return String.format("hauptbuch.jsf?faces-redirect=true&id=%s", this.id);
    }
}
