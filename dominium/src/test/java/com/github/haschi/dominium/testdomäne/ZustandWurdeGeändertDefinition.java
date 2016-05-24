package com.github.haschi.dominium.testdomäne;

import com.github.haschi.dominium.modell.Domänenereignis;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;
import org.immutables.value.Value.Style;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Immutable
@Style(
    typeAbstract = "*Definition",
    typeImmutable = "*",
    build = "erzeugen",
    builder = "erbauer",
    privateNoargConstructor = true,
    typeBuilder = "Erbauer",
    typeInnerBuilder = "Erbauer",
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
