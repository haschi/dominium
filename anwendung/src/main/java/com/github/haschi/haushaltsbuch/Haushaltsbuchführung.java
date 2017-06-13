package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung;
import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Named
@RequestScoped
@SuppressWarnings("checkstyle:designforextension")
// @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Haushaltsbuchführung
        implements Serializable
{

    private static final long serialVersionUID = 5484105498393122925L;

    @Inject
    private CommandGateway commandGateway;
    private String identitätsmerkmal = "";

    public Haushaltsbuchführung()
    {
    }

    public String getIdentitätsmerkmal()
    {
        return this.identitätsmerkmal;
    }

    public String beginnen()
    {
        this.identitätsmerkmal = UUID.randomUUID().toString();

        final BeginneHaushaltsbuchführung befehl = ImmutableBeginneHaushaltsbuchführung.builder()
                .id(UUID.fromString(this.identitätsmerkmal))
                .build();

        this.commandGateway.sendAndWait(befehl);
        return String.format("hauptbuch.jsf?faces-redirect=true&id=%s", this.identitätsmerkmal);
    }
}
