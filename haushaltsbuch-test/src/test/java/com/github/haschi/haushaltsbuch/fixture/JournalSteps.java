package com.github.haschi.haushaltsbuch.fixture;

import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;

import static org.assertj.core.api.Assertions.assertThat;

public class JournalSteps implements AbstractJournalSteps
{
    private final Ereignisquelle ereignisquelle;
    private final Aggregatkennung haushaltsbuchId;

    public JournalSteps(final Ereignisquelle ereignisquelle, final Aggregatkennung haushaltsbuchId)
    {
        this.ereignisquelle = ereignisquelle;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void angelegt()
    {
        assertThat(ereignisquelle.ereignisseLesen(haushaltsbuchId))
                .contains(ImmutableJournalWurdeAngelegt.builder()
                                  .aktuelleHaushaltsbuchId(haushaltsbuchId)
                                  .build());
    }
}
