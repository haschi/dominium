package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.testdomaene.ÄndereZustand;
import org.immutables.value.Value;

@Value.Immutable(builder = false)
public abstract class ÄndereZustandCommand implements Command {
    @Value.Parameter
    public abstract ÄndereZustand command();

    @Override
    public final void apply(final CommandDispatcher dispatcher) {
        dispatcher.ausführen(this.command());
    }
}
