package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;

import static org.assertj.core.api.Assertions.assertThat;

public class JournalSteps implements AbstractJournalSteps
{
    private final Ereignismonitor ereignismonitor;
    private final Aggregatkennung haushaltsbuchführung;

    public JournalSteps(final Ereignismonitor ereignismonitor, final Aggregatkennung haushaltsbuchführung)
    {
        this.ereignismonitor = ereignismonitor;
        this.haushaltsbuchführung = haushaltsbuchführung;
    }

    @Override
    public void journalAngelegt()
    {
        assertThat(ereignismonitor.erwarteteEreignisse().get(1))
                .isEqualTo(ImmutableJournalWurdeAngelegt.builder()
                                   .aktuelleHaushaltsbuchId(this.haushaltsbuchführung)
                                   .build());
    }
}
