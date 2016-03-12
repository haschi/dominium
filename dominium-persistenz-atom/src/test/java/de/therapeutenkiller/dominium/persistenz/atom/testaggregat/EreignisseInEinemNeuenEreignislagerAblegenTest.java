package de.therapeutenkiller.dominium.persistenz.atom.testaggregat;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.Versionsbereich;
import de.therapeutenkiller.dominium.persistenz.atom.AtomEreignisLager;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public final class EreignisseInEinemNeuenEreignislagerAblegenTest {

    @Parameterized.Parameters()
    public static Iterable<Object[]> testdaten() {
        return Arrays.asList(new Object[][] {
                {1}, {19}, {20}, {21}, {99}, {301}
        });
    }

    public EreignisseInEinemNeuenEreignislagerAblegenTest(final int ereignisanzahl) {
        this.ereignisanzahl = ereignisanzahl;
    }

    private int ereignisanzahl;

    private final UUID identitätsmerkmal = UUID.randomUUID();

    @Before
    public void wenn_ein_neues_ereignislager_mit_ereignissen_angelegt_wird() {

        final TestAggregat aggregat = new TestAggregat(this.identitätsmerkmal);
        aggregat.ereignisseErzeugen(this.ereignisanzahl);

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

        assertThat(ereignisliste).hasSize(this.ereignisanzahl);

        ereignisliste.stream()
            .map(ereignis -> {
                final TestAggregat ziel = new TestAggregat(UUID.randomUUID());
                ereignis.anwendenAuf(ziel);
                return ziel.getWert();
            })
            .reduce(Long.MIN_VALUE, (Long kleiner, Long größer) -> {
                assertThat(kleiner).isLessThan(größer);
                return größer;
            });
    }
}
