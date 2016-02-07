package de.therapeutenkiller.dominium.persistenz.jpa;

import de.therapeutenkiller.dominium.modell.Domänenereignis;
import de.therapeutenkiller.dominium.persistenz.jpa.testaggregat.TestAggregat;

import org.junit.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;

public final class SerialisierungVonDomänenereignisseDurchUmschlag {

    @Test
    public void ausnahme_für_nicht_serialisierbare_domänenereignisse() {

        final Domänenereignis<TestAggregat> ereignis = new NichtSerialisierbar();
        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L);

        final Throwable thrown = catchThrowable( () -> {
            new JpaDomänenereignisUmschlag<>(ereignis, meta);
        });

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
        ;
    }

    @Test
    public void ausnahme_für_nicht_deserialisierbare_domänenereignisse() {
        final Domänenereignis<TestAggregat> ereignis = new NichtDeserialisierbar();

        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L);
        final JpaDomänenereignisUmschlag<TestAggregat> umschlag = new JpaDomänenereignisUmschlag<>(ereignis, meta);

        final Throwable thrown = catchThrowable(() -> {
            umschlag.öffnen();
        });

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void ausnahme_für_domänenereignisse_mit_fehlender_klasse() {
        final Domänenereignis<TestAggregat> ereignis = new NichtDeserialisierbarWegenFehlenderKlasse();

        final JpaEreignisMetaDaten meta = new JpaEreignisMetaDaten("test-strom", 1L);
        final JpaDomänenereignisUmschlag<TestAggregat> umschlag = new JpaDomänenereignisUmschlag<>(ereignis, meta);

        final Throwable thrown = catchThrowable(() -> {
            umschlag.öffnen();
        });

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
