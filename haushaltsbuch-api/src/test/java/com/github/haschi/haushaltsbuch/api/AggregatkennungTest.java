package com.github.haschi.haushaltsbuch.api;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AggregatkennungTest
{
    @Test
    public void erfüllt_äquivalenzregeln()
    {
        EqualsVerifier.forClass(Aggregatkennung.class)
                .suppress(Warning.NULL_FIELDS)
                .verify();
    }

    @Test
    public void kann_neuen_wert_erzeugen()
    {
        final _Aggregatkennung aggregatkennung = Aggregatkennung.neu();
        assertThat(aggregatkennung.wert()).isNotNull();
    }
}
