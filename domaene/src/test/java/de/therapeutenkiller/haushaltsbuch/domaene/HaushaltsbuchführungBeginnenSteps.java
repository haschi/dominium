package de.therapeutenkiller.haushaltsbuch.domaene;

import com.google.common.collect.ImmutableCollection;
import cucumber.api.Transform;
import cucumber.api.java.de.Dann;
import cucumber.api.java.de.Wenn;
import de.therapeutenkiller.haushaltsbuch.abfrage.AlleHaushaltsbücher;
import de.therapeutenkiller.haushaltsbuch.api.kommando.HaushaltsbuchführungBeginnenKommando;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.DieWelt;
import de.therapeutenkiller.haushaltsbuch.domaene.testsupport.MoneyConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.money.MonetaryAmount;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Singleton
public final class HaushaltsbuchführungBeginnenSteps {

    private final DieWelt kontext;
    private final AlleHaushaltsbücher alleHaushaltsbücher;

    @Inject
    public HaushaltsbuchführungBeginnenSteps(final DieWelt kontext, final AlleHaushaltsbücher alleHaushaltsbücher) {

        this.kontext = kontext;
        this.alleHaushaltsbücher = alleHaushaltsbücher;
    }

    @Wenn("^ich mit der Haushaltsbuchführung beginne$")
    public void ich_mit_der_Haushaltsbuchführung_beginne() {
        final UUID haushaltsbuchId = UUID.randomUUID();
        this.kontext.kommandoAusführen(new HaushaltsbuchführungBeginnenKommando(haushaltsbuchId));
        this.kontext.setAktuelleHaushaltsbuchId(haushaltsbuchId);
    }

    @Wenn("^ich nicht mit der Haushaltsbuchführung beginne$")
    public void ich_nicht_mit_der_Haushaltsbuchführung_beginne() {
    }

    @Dann("^werde ich ein neues Haushaltsbuch angelegen$")
    public void dann_wird_ein_neues_haushaltsbuch_angelegt_worden_sein()  {

        final ImmutableCollection<UUID> alle = this.alleHaushaltsbücher.abfragen();
        alle.contains(this.kontext.getAktuelleHaushaltsbuchId());
    }

    // TODO Dieser Step muss noch implementiert werden.
    @Dann("^(?:ich werde|werde ich) ein Gesamtvermögen von (-?\\d+,\\d{2} [A-Z]{3}) besitzen$")
    public void ich_werde_ein_Gesamtvermögen_besitzen(
        @Transform(MoneyConverter.class) final MonetaryAmount währungsbetrag) {

        // Überlegen, wie die Abfrage ausgeführt wird, wenn kein Haushaltsbuch existiert.
        // final UUID haushaltsbuchId = this.kontext.getHaushaltsbuch().getIdentitätsmerkmal();
        // final MonetaryAmount actual = this.gesamtvermögenBerechnen.abfragen(haushaltsbuchId);

        // assertThat(actual).isEqualTo(währungsbetrag); // NOPMD
    }

    @Dann("^werde ich kein neues Haushaltsbuch anlegen$")
    public void werde_ich_kein_neues_Haushaltsbuch_angelegt_haben() {
        assertThat(this.alleHaushaltsbücher.abfragen()).isEmpty();
    }
}
