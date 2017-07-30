package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractJournalSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableJournalWurdeAngelegt;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JournalSteps implements AbstractJournalSteps
{
    private final Ereignismonitor monitor;
    private final Aggregatkennung haushaltsbuchführungId;

    public JournalSteps(final Ereignismonitor monitor, final Aggregatkennung haushaltsbuchführungId)
    {

        this.monitor = monitor;
        this.haushaltsbuchführungId = haushaltsbuchführungId;
    }

    @Override
    public void angelegt()
    {
        assertThat(monitor.erwarteteEreignisse().get(1)).isEqualTo(
                ImmutableJournalWurdeAngelegt.builder()
                        .aktuelleHaushaltsbuchId(haushaltsbuchführungId)
                        .build());
    }
}
