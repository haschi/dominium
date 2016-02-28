package de.therapeutenkiller.haushaltsbuch;

import de.therapeutenkiller.dominium.persistenz.KonkurrierenderZugriff;
import de.therapeutenkiller.haushaltsbuch.anwendungsfall.HaushaltsbuchführungBeginnen;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Named
@RequestScoped
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

    public String beginnen() throws IOException, KonkurrierenderZugriff {
        this.identitätsmerkmal = UUID.randomUUID().toString();

        final BeginneHaushaltsbuchführung befehl = new BeginneHaushaltsbuchführung(
                UUID.fromString(this.identitätsmerkmal));

        this.beginnen.ausführen(befehl);

        return String.format("hauptbuch.jsf?faces-redirect=true&id=%s", identitätsmerkmal);
    }
}
