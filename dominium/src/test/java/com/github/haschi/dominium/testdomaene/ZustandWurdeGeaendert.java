package com.github.haschi.dominium.testdomaene;

import org.immutables.value.Value;

@Value.Immutable(builder = false)
public interface ZustandWurdeGeaendert {
    @Value.Parameter long payload();
}
