package com.github.haschi.dominium.persistenz.jpa;

import com.github.haschi.dominium.persistenz.AggregatNichtGefunden;
import com.github.haschi.testing.DatenbankRegel;
import com.github.haschi.testing.TestUhr;
import com.github.haschi.dominium.modell.Schnappschuss;
import com.github.haschi.dominium.persistenz.jpa.aggregat.TestSchnappschuss;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class SchnappschussAusEinemLeerenSchnappschussLagerAbholenTest {

    @Rule
    public DatenbankRegel datenbankRegel = new DatenbankRegel();

    private JpaSchnappschussLager<TestSchnappschuss> store;
    private final TestUhr uhr = new TestUhr();
    private final UUID id = UUID.randomUUID();

    @Before
    public void angenommen_ich_habe_ein_ereignislager_ohne_schnappschüsse_angelegt() {
        this.store = new JpaSchnappschussLager<>(
            this.datenbankRegel.getEntityManager(),
            this.uhr);
    }

    @Test
    public void wenn_ich_den_neuesten_schnappschuss_anfordere() throws AggregatNichtGefunden {
        final Optional<TestSchnappschuss> schnappschuss =
                this.store.getNeuesterSchnappschuss(this.id);

        this.dann_werde_ich_keinen_schnappschuss_erhalten(schnappschuss.orElse(TestSchnappschuss.LEER));
    }

    private void dann_werde_ich_keinen_schnappschuss_erhalten(final Schnappschuss<UUID> schnappschuss) {

        assertThat(schnappschuss).isEqualTo(TestSchnappschuss.LEER);
    }
}
