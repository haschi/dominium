package com.github.haschi.dominium.haushaltsbuch.core.domain

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import org.axonframework.eventsourcing.DomainEventMessage

interface Historie
{
    fun bezueglich(aggregat: Aggregatkennung): Sequence<DomainEventMessage<*>>
}