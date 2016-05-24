package com.github.haschi.dominium.testdomäne;

import com.github.haschi.dominium.modell.Domänenereignis;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Immutable(builder = false)
@Value.Style(
    typeAbstract = "*Definition",
    typeImmutable = "*",
    build = "erzeugen",
    visibility = ImplementationVisibility.PUBLIC)
abstract class ZustandWurdeGeändertDefinition implements Domänenereignis<TestAggregatEreignisZiel> {

    private static final long serialVersionUID = 5894690972126209956L;

    @Parameter
    public abstract long payload();

    @Override
    public final void anwendenAuf(final TestAggregatEreignisZiel ereignisZiel) {
        ereignisZiel.falls(this);
    }
}
