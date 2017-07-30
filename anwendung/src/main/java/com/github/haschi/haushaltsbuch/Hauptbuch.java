package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAbfrage;
import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAnsicht;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
@SuppressWarnings("checkstyle:designforextension")
public class Hauptbuch
        implements Serializable
{

    private static final long serialVersionUID = 7723531928992579461L;

    private HauptbuchAnsicht ansicht;
    private String id = "";
    @Inject
    private HauptbuchAbfrage abfrage;

    public Hauptbuch()
    {
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public List<String> getVermögen()
    {
        return this.ansicht.aktivkonten();
    }

    public List<String> getSchulden()
    {
        return this.ansicht.passivkonten();
    }

    public List<String> getEinnahmen()
    {
        return this.ansicht.ertragskonten();
    }

    public List<String> getAusgaben()
    {
        return this.ansicht.aufwandskonten();
    }

    public void init()
    {
        this.ansicht = this.abfrage.abfragen(Aggregatkennung.of(this.id));
    }
}
