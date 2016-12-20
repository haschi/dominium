package domaene;

import com.github.haschi.haushaltsbuch.api.ereignis.ImmutableHauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.ereignis.ImmutableHaushaltsbuchAngelegt;
import com.github.haschi.haushaltsbuch.api.ereignis.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.api.kommando.ImmutableBeginneHaushaltsbuchführung;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import org.axonframework.commandhandling.gateway.CommandGateway;
import testsupport.DieWelt;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class HaushaltsbuchführungBeginnenSteps
{
    private final CommandGateway commandGateway;

    private final DieWelt welt;

    @Inject
    public HaushaltsbuchführungBeginnenSteps(final CommandGateway commandGateway, final DieWelt welt)
    {
        this.commandGateway = commandGateway;
        this.welt = welt;
    }

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ich_mit_der_Haushaltsbuchführung_beginne()
    {
        final UUID haushaltsbuchId = UUID.randomUUID();
        this.commandGateway.sendAndWait(ImmutableBeginneHaushaltsbuchführung.builder().id(haushaltsbuchId).build());
        this.welt.setAktuelleHaushaltsbuchId(haushaltsbuchId);
    }

    @Dann("^werde ich ein neues Haushaltsbuch angelegt haben")
    public void dann_werde_ich_ein_neues_haushaltsbuch_angelegt_haben()
    {
        assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableHaushaltsbuchAngelegt.builder()
                                                                        .id(this.welt.getAktuelleHaushaltsbuchId())
                                                                        .build());
    }

    @Dann("^werde ich ein Hauptbuch mit Kontenrahmen zum Haushaltsbuch angelegt haben")
    public void dann_werde_ich_ein_hauptbuch_mit_kontenrahmen_zum_haushaltsbuch_anlegen()
    {

        assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableHauptbuchWurdeAngelegt.builder()
                                                                        .haushaltsbuchId(this.welt
                                                                                                 .getAktuelleHaushaltsbuchId())
                                                                        .build());
    }

    @Dann("^werde ich ein Journal zum Haushaltsbuch angelegt haben")
    public void dann_werde_ich_ein_journal_zum_haushaltsbuch_anlegen()
    {
        assertThat(this.welt.aktuellerEreignisstrom()).contains(ImmutableJournalWurdeAngelegt.builder()
                                                                        .aktuelleHaushaltsbuchId(this.welt
                                                                                                         .getAktuelleHaushaltsbuchId())
                                                                        .build());
    }
}
