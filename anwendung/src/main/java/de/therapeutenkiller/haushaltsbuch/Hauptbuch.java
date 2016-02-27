package de.therapeutenkiller.haushaltsbuch;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@SuppressWarnings("checkstyle:designforextension")
public class Hauptbuch implements Serializable {

    private HauptbuchAnsicht ansicht;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String id = "";

    @Inject
    private HauptbuchAbfrage abfrage;

    private List<String> getAktivkonten() {
        return this.ansicht.aktivkonten;
    }

    public void init() {
        // this.ansicht = this.abfrage.abfragen(this.hauptbuchId);
    }
}
