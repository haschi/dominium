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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Named
@RequestScoped
@SuppressWarnings("checkstyle:designforextension")
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Haushaltsbuchführung implements Serializable {

    @Inject
    private Event<BeginneHaushaltsbuchführung> beginneHaushaltsbuchführungEvent;

    @Inject
    private HaushaltsbuchführungBeginnen beginnen;

    public String getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    private String identitätsmerkmal = "";

    @PersistenceContext
    private EntityManager em;

    public String beginnen() throws IOException, KonkurrierenderZugriff {
        this.identitätsmerkmal = UUID.randomUUID().toString();

        final BeginneHaushaltsbuchführung befehl = new BeginneHaushaltsbuchführung(
                UUID.fromString(this.identitätsmerkmal));

        this.beginneHaushaltsbuchführungEvent.fire(befehl);
        return String.format("hauptbuch.jsf?faces-redirect=true&id=%s", this.identitätsmerkmal);
    }
}
