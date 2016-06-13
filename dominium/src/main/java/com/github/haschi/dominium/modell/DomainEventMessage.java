package com.github.haschi.dominium.modell;

import org.immutables.value.Value;

@Value.Immutable(builder = false)
public interface DomainEventMessage<I, T> {
    @Value.Parameter I identitaetsmerkmal();
    @Value.Parameter T event();
    @Value.Parameter long sequenceNumber();
}
