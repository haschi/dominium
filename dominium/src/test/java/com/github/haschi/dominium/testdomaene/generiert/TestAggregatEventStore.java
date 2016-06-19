package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.infrastructure.MemoryEventStore;

import java.util.UUID;

public final class TestAggregatEventStore extends MemoryEventStore<TestAggregatEvent, UUID> {
}
