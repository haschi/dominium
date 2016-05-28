package com.github.haschi.dominium.testdomäne;

import com.github.haschi.dominium.modell.Domänenereignis;
import com.github.haschi.dominium.modell.DomänenereignisDefinition;
import com.github.haschi.dominium.modell.EreignisZiel;
import org.immutables.value.Value.Parameter;

@DomänenereignisDefinition
abstract class ZustandWurdeGeändertDefinition implements Domänenereignis<TestAggregatEreignisZiel> {

    private static final long serialVersionUID = 5894690972126209956L;

    @Parameter
    public abstract long payload();

    @Override
    public final void anwendenAuf(final TestAggregatEreignisZiel ereignisZiel) {
        ereignisZiel.falls(this);
    }

    @Override
    public final void anwendenAuf(final EreignisZiel<TestAggregatEreignisZiel> ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
