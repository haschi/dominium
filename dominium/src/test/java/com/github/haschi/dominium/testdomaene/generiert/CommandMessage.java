package com.github.haschi.dominium.testdomaene.generiert;

import org.immutables.value.Value;

@Value.Immutable(builder = false)
public interface CommandMessage<T, X> {
    @Value.Parameter T command();

    @Value.Parameter Class<X> handler();
}
