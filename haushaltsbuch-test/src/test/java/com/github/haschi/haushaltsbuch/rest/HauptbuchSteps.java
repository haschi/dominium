package com.github.haschi.haushaltsbuch.rest;

import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchführungBegonnen;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;

import static org.assertj.core.api.Assertions.assertThat;

public class HauptbuchSteps implements AbstractHauptbuchSteps
{
    private final Ereignismonitor monitor;
    private final Aggregatkennung haushaltsbuchId;

    public HauptbuchSteps(final Ereignismonitor monitor, final Aggregatkennung haushaltsbuchId) {

        this.monitor = monitor;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void angelegt()
    {
        assertThat(monitor.erwarteteEreignisse().get(0)).isEqualTo(
                ImmutableHaushaltsbuchführungBegonnen.builder()
                        .id(this.haushaltsbuchId)
                        .build());
    }
}
