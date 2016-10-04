package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoAn;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoAn;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named
@RequestScoped
@SuppressWarnings("checkstyle:designforextension")
public class KontoAnlegen
{

    private String id = "";

    public String getId()
    {
        return this.id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    private String kontoname = "";

    public String getKontoname()
    {
        return this.kontoname;
    }

    public void setKontoname(final String kontoname)
    {
        this.kontoname = kontoname;
    }

    private Kontoart kontoart = Kontoart.Aktiv;

    public Kontoart getKontoart()
    {
        return this.kontoart;
    }

    public void setKontoart(final Kontoart kontoart)
    {
        this.kontoart = kontoart;
    }

    public Kontoart[] getKontoarten()
    {
        return Kontoart.values();
    }

    @Inject
    private CommandGateway commandGateway;

    public String ausführen()
    {
        final LegeKontoAn befehl = ImmutableLegeKontoAn
                .builder()
                .haushaltsbuchId(UUID.fromString(this.id))
                .kontoname(this.kontoname)
                .kontoart(this.kontoart)
                .build();

        this.commandGateway.sendAndWait(befehl);

        return String.format("hauptbuch.jsf?faces-redirect=true&id=%s", this.id);
    }
}
