package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.testdomaene.ÄndereZustand;
import org.immutables.value.Value;

@Value.Immutable(builder = false)
public abstract class ÄndereZustandCommand implements Command {
    @Value.Parameter
    public abstract ÄndereZustand command();
}
