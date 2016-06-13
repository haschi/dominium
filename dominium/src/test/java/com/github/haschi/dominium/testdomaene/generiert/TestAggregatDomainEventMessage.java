package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.modell.DomainEventMessage;

import java.util.UUID;

public interface TestAggregatDomainEventMessage extends DomainEventMessage<UUID, TestAggregatEreignis> {

}
