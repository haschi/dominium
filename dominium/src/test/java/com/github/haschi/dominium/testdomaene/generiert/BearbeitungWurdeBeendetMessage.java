package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.testdomaene.BearbeitungWurdeBeendet;
import org.immutables.value.Value;

@Value.Immutable(builder = false)
public abstract class BearbeitungWurdeBeendetMessage implements TestAggregatEreignis {

    @Value.Parameter
    public abstract BearbeitungWurdeBeendet ereignis();

    @Override
    public void anwendenAuf(final TestAggregatProxy testAggregatProxy) {
        testAggregatProxy.verarbeiten(this);
    }
}
