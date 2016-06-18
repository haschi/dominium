package com.github.haschi.dominium.infrastructure;

import com.github.haschi.dominium.modell.Version;
import org.immutables.value.Value;

@Value.Immutable(builder = false)
public interface EventStream<T> {

    @Value.Parameter Iterable<T> events();

    @Value.Parameter Version version();
}
