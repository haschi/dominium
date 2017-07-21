package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.Kontendefinition;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKontenrahmenAngelegt;
import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableKonto;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HauptbuchSteps extends AbstractHauptbuchSteps
{
    private final Ereignismonitor ereignismonitor;

    public HauptbuchSteps(final Ereignismonitor ereignismonitor)
    {
        this.ereignismonitor = ereignismonitor;
    }

    @Override
    public void kontenrahmenAngelegt(final List<Kontendefinition> konten)
    {
            assertThat(ereignismonitor.erwarteteEreignisse().get(2))
                    .isEqualTo(ImmutableKontenrahmenAngelegt.builder()
                                       .addAllKonten(konten.stream().map(k -> ImmutableKonto.builder()
                                               .bezeichnung(k.bezeichnung)
                                               .art(k.art)
                                               .nummer(k.nummer)
                                               .build())
                                                             .collect(Collectors.toList()))
                                       .build());
    }
}
