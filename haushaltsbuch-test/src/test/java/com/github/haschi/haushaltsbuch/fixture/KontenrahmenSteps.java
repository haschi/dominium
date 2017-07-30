package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractKontenrahmenSteps;
import com.github.haschi.haushaltsbuch.Kontendefinition;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKontenrahmenAngelegt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKonto;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class KontenrahmenSteps implements AbstractKontenrahmenSteps
{
    private final Ereignisquelle ereignisquelle;
    private final Aggregatkennung haushaltsbuchkennung;

    public KontenrahmenSteps(final Ereignisquelle ereignisquelle, final Aggregatkennung haushaltsbuchkennung) {

        this.ereignisquelle = ereignisquelle;
        this.haushaltsbuchkennung = haushaltsbuchkennung;
    }
    @Override
    public void angelegt(final List<Kontendefinition> konten)
    {
        assertThat(ereignisquelle.ereignisseLesen(haushaltsbuchkennung))
                .contains(ImmutableKontenrahmenAngelegt.builder()
                          .addAllKonten(konten.stream()
                            .map(k -> ImmutableKonto.builder()
                                .nummer(k.nummer)
                                .bezeichnung(k.bezeichnung)
                                .art(k.art)
                                .build())
                            .collect(Collectors.toList()))
                          .build());
    }
}
