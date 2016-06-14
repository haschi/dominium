package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.testdomaene.ZustandWurdeGeaendert;
import org.immutables.value.Value;

@Value.Immutable(builder = false)
public abstract class ZustandWurdeGeaendertMessage implements TestAggregatEvent {

    @Value.Parameter
    public abstract ZustandWurdeGeaendert ereignis();

    public final void anwendenAuf(final TestAggregatProxy aggregat) {
        aggregat.verarbeiten(this);
    }
}
