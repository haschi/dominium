package com.github.haschi.dominium.testdomaene;

import com.github.haschi.coding.annotation.TargetAggregateIdentifier;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable(builder = false)
public interface ÄndereZustand {
    @TargetAggregateIdentifier()
    @Value.Parameter
    UUID testAggregateId();

    @Value.Parameter
    long payload();
}
