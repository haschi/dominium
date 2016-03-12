package de.therapeutenkiller.dominium.persistenz.atom.testaggregat;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import de.therapeutenkiller.dominium.persistenz.atom.AtomEreignisLager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public final class EreignisseInEinemNeuenEreignislagerAblegenTest {

    private final UUID identitätsmerkmal = UUID.randomUUID();

    @Before
    public void wenn_ein_neues_ereignislager_mit_ereignissen_angelegt_wird() {


        final TestAggregat aggregat = new TestAggregat(this.identitätsmerkmal);
        aggregat.einenZustandÄndern(42L);
        aggregat.einenZustandÄndern(43L);

        final AtomEreignisLager<TestAggregat, TestAggregatEreignisziel> store = new AtomEreignisLager<>();

        store.neuenEreignisstromErzeugen(
            this.identitätsmerkmal,
            aggregat.getÄnderungen());
    }

    @Test
    public void dann_werden_die_ereignisse_gespeichert() {

        final AtomEreignisLager<TestAggregat, TestAggregatEreignisziel> store = new AtomEreignisLager<>();

        final List<Domänenereignis<TestAggregatEreignisziel>> ereignisliste = store.getEreignisliste(
                this.identitätsmerkmal,
                Versionsbereich.ALLE_VERSIONEN);

        assertThat(ereignisliste)
                .containsExactly(new ZustandWurdeGeändert(42L), new ZustandWurdeGeändert(43L));
    }
}
