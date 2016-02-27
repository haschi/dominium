package de.therapeutenkiller.haushaltsbuch;

import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
@SuppressWarnings("checkstyle:designforextension")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class Haushaltsbuchführung implements Serializable {

    @Inject
    private Event<BeginneHaushaltsbuchführung> beginneHaushaltsbuchführungEvent;

    @Inject
    private HaushaltsbuchführungBeginnen beginnen;

    public String getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    private String identitätsmerkmal = "";

    public void beginnen() throws IOException, KonkurrierenderZugriff {
        this.identitätsmerkmal = UUID.randomUUID().toString();
        final BeginneHaushaltsbuchführung befehl = new BeginneHaushaltsbuchführung(
                UUID.fromString(this.identitätsmerkmal));
        // this.beginneHaushaltsbuchführungEvent.fire(befehl);
        this.beginnen.ausführen(befehl);

        final String url = String.format("hauptbuch.jsf?id=%s", this.identitätsmerkmal);
        // FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
}
