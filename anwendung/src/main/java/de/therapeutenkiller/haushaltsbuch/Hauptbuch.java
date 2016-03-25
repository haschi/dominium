package de.therapeutenkiller.haushaltsbuch;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
@SuppressWarnings("checkstyle:designforextension")
public class Hauptbuch implements Serializable {

    //private HauptbuchAnsicht ansicht;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    private String id = "";

    //@Inject
    //private HauptbuchAbfrage abfrage;

    public List<String> getVermögen() {
        //return this.ansicht.aktivkonten;
        return new ArrayList<String>();
    }

    public List<String> getSchulden() {
        // return this.ansicht.passivkonten;
        return new ArrayList<String>();
    }

    public List<String> getEinnahmen() {
        // return this.ansicht.ertragskonten;
        return new ArrayList<String>();
    }

    public List<String> getAusgaben() {
        //return this.ansicht.aufwandskonten;
        return new ArrayList<String>();
    }

    public void init() {
        // this.ansicht = this.abfrage.abfragen(UUID.fromString(this.id));
    }
}
