package de.therapeutenkiller.haushaltsbuch;

import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Named
@Stateless
@SuppressWarnings("checkstyle:designforextension")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class Haushaltsbuchführung implements Serializable {

    @Inject
    private Event<BeginneHaushaltsbuchführung> beginneHaushaltsbuchführungEvent;

    public void beginnen() {
        final BeginneHaushaltsbuchführung befehl = new BeginneHaushaltsbuchführung(UUID.randomUUID());
        this.beginneHaushaltsbuchführungEvent.fire(befehl);
    }
}
