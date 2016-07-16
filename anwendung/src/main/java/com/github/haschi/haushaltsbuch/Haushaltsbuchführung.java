package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.kommando.BeginneHaushaltsbuchfuehrung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

//@Named
//@RequestScoped
@SuppressWarnings("checkstyle:designforextension")
// @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Haushaltsbuchführung implements Serializable {

    private static final long serialVersionUID = 5484105498393122925L;

    @Inject
    private CommandGateway commandGateway;


    public String getIdentitätsmerkmal() {
        return this.identitätsmerkmal;
    }

    private String identitätsmerkmal = "";

    public String beginnen() throws IOException {
        this.identitätsmerkmal = UUID.randomUUID().toString();

        final BeginneHaushaltsbuchfuehrung befehl = ImmutableBeginneHaushaltsbuchfuehrung.builder()
                .id(UUID.fromString(this.identitätsmerkmal))
                .build();

        this.commandGateway.sendAndWait(befehl);
        return String.format("hauptbuch.jsf?faces-redirect=true&id=%s", this.identitätsmerkmal);
    }
}
