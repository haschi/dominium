package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableHauptbuchWurdeAngelegt;

import static org.assertj.core.api.Assertions.assertThat;

public class HauptbuchSteps implements AbstractHauptbuchSteps
{
    private final Ereignisquelle ereignisquelle;
    private final Aggregatkennung haushaltsbuchkennung;

    public HauptbuchSteps(
            final Ereignisquelle ereignisquelle,
            final Aggregatkennung haushaltsbuchkennung)
    {

        this.ereignisquelle = ereignisquelle;
        this.haushaltsbuchkennung = haushaltsbuchkennung;
    }


    @Override
    public void angelegt()
    {
        assertThat(ereignisquelle.ereignisseLesen(haushaltsbuchkennung))
                .contains(ImmutableHauptbuchWurdeAngelegt.builder()
                                .haushaltsbuchId(haushaltsbuchkennung)
                                  .build());
    }
}
