package de.therapeutenkiller.haushaltsbuch.domaene;

import com.google.common.collect.ImmutableCollection;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.abfrage.AlleHaushaltsbücher;
import de.therapeutenkiller.haushaltsbuch.api.kommando.BeginneHaushaltsbuchführung;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.HauptbuchWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.ereignis.JournalWurdeAngelegt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class HaushaltsbuchführungBeginnenSteps {

    @Inject
    Event<BeginneHaushaltsbuchführung> beginneHaushaltsbuchführung;

    @Inject
    DieWelt kontext;

    @Inject
    AlleHaushaltsbücher alleHaushaltsbücher;

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ich_mit_der_Haushaltsbuchführung_beginne() {
        final UUID haushaltsbuchId = UUID.randomUUID();
        this.beginneHaushaltsbuchführung.fire(new BeginneHaushaltsbuchführung(haushaltsbuchId));
        this.kontext.setAktuelleHaushaltsbuchId(haushaltsbuchId);
    }

    @Wenn("^ich nicht mit der Haushaltsbuchführung beginne$")
    public void ich_nicht_mit_der_Haushaltsbuchführung_beginne() {
    }

    @Dann("^werde ich ein neues Haushaltsbuch angelegt haben")
    public void dann_werde_ich_ein_neues_haushaltsbuch_angelegt_haben()  {

        final ImmutableCollection<UUID> alle = this.alleHaushaltsbücher.abfragen();
        alle.contains(this.kontext.getAktuelleHaushaltsbuchId());
    }

    @Dann("^werde ich ein Hauptbuch mit Kontenrahmen zum Haushaltsbuch angelegt haben")
    public void dann_werde_ich_ein_hauptbuch_mit_kontenrahmen_zum_haushaltsbuch_anlegen() {

        assertThat(this.kontext.aktuellerEreignisstrom())
                .contains(new HauptbuchWurdeAngelegt(this.kontext.getAktuelleHaushaltsbuchId()));
    }

    @Dann("^werde ich ein Journal zum Haushaltsbuch angelegt haben")
    public void dann_werde_ich_ein_journal_zum_haushaltsbuch_anlegen() {
        assertThat(this.kontext.aktuellerEreignisstrom())
                .contains(new JournalWurdeAngelegt(this.kontext.getAktuelleHaushaltsbuchId()));
    }

    @Dann("^werde ich kein neues Haushaltsbuch anlegen$")
    public void werde_ich_kein_neues_Haushaltsbuch_angelegt_haben() {
        assertThat(this.alleHaushaltsbücher.abfragen()).isEmpty();
    }
}
