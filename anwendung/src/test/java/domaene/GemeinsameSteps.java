package domaene;

import com.github.haschi.haushaltsbuch.api.Kontoart;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchfuehrung;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableLegeKontoAn;
import com.github.haschi.haushaltsbuch.api.kommando.LegeKontoAn;
import cucumber.api.java.de.Angenommen;
import org.axonframework.commandhandling.gateway.CommandGateway;
import testsupport.DieWelt;

import javax.inject.Inject;
import java.util.UUID;

public final class GemeinsameSteps
{

    @Inject
    private DieWelt welt;

    @Inject
    private CommandGateway commandGateway;

    @Angenommen("^ich habe mit der Haushaltsbuchführung begonnen$")
    public void ich_habe_mit_der_Haushaltsbuchführung_begonnen()
    {
        final UUID identitätsmerkmal = UUID.randomUUID();

        this.commandGateway.sendAndWait(ImmutableBeginneHaushaltsbuchfuehrung.builder().id(identitätsmerkmal).build());

        this.welt.setAktuelleHaushaltsbuchId(identitätsmerkmal);
    }

    @Angenommen("ich habe das Konto \"([^\"]*)\" angelegt")
    public void ich_habe_das_Konto_angelegt(final String kontoname)
    {

        final LegeKontoAn kommando = ImmutableLegeKontoAn
                .builder()
                .haushaltsbuchId(this.welt.getAktuelleHaushaltsbuchId())
                .kontoname(kontoname)
                .kontoart(Kontoart.Aktiv)
                .build();

        this.commandGateway.sendAndWait(kommando);
    }
}
