package com.github.haschi.haushaltsbuch;

//import de.therapeutenkiller.haushaltsbuch.api.kommando.BucheAusgabe;

import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAbfrage;
import com.github.haschi.haushaltsbuch.abfragen.HauptbuchAnsicht;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.kommando.BucheAusgabe;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBucheAusgabe;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

//@ViewScoped
//@ManagedBean
@SuppressWarnings("checkstyle:designforextension")
public class AusgabeBuchen
        implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String id = "";
    private HauptbuchAnsicht hauptbuch;

    @SuppressWarnings("CanBeFinal")
    @Inject
    private HauptbuchAbfrage abfrage;

    private String sollkonto = "";
    private String habenkonto = "";
    private String betrag = "";

    @SuppressWarnings("CanBeFinal")
    @Inject
    private CommandGateway commandGateway;

    public String getId()
    {
        return this.id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public void init()
    {
        this.hauptbuch = this.abfrage.abfragen(Aggregatkennung.of(this.id));
    }

    public List<String> getAktivkonten()
    {
        return this.hauptbuch.aktivkonten();
    }

    public String getSollkonto()
    {
        return this.sollkonto;
    }

    public void setSollkonto(final String sollkonto)
    {
        this.sollkonto = sollkonto;
    }

    public List<String> getAufwandskonten()
    {
        return this.hauptbuch.aufwandskonten();
    }

    public String getHabenkonto()
    {
        return this.habenkonto;
    }

    public void setHabenkonto(final String habenkonto)
    {
        this.habenkonto = habenkonto;
    }

    public String getBetrag()
    {
        return this.betrag;
    }

    public void setBetrag(final String betrag)
    {
        this.betrag = betrag;
    }

    public String ausführen()
    {
        final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        final MonetaryAmount währungsbetrag = format.parse(this.betrag + " EUR");

        final BucheAusgabe befehl = ImmutableBucheAusgabe.builder()
                .haushaltsbuchId(Aggregatkennung.of(this.id))
                .sollkonto(this.sollkonto)
                .habenkonto(this.habenkonto)
                .geldbetrag(währungsbetrag)
                .build();

        this.commandGateway.sendAndWait(befehl);
        return String.format("hauptbuch?faces-redirect=true&id=%s", this.id);
    }
}
