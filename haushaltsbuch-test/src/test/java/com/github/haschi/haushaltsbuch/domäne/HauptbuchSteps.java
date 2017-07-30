package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.AbstractHauptbuchSteps;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableHauptbuchWurdeAngelegt;
import com.github.haschi.haushaltsbuch.infrastruktur.Ereignismonitor;

import static org.assertj.core.api.Assertions.assertThat;

public class HauptbuchSteps implements AbstractHauptbuchSteps
{
    private final Ereignismonitor ereignismonitor;
    private final Aggregatkennung haushaltsbuchId;

    public HauptbuchSteps(final Ereignismonitor ereignismonitor, final Aggregatkennung haushaltsbuchId)
    {
        this.ereignismonitor = ereignismonitor;
        this.haushaltsbuchId = haushaltsbuchId;
    }

    @Override
    public void angelegt()
    {
        assertThat(ereignismonitor.erwarteteEreignisse().get(3)).isEqualTo(
                ImmutableHauptbuchWurdeAngelegt.builder()
                        .haushaltsbuchId(this.haushaltsbuchId)
                        .build());

    }
}
