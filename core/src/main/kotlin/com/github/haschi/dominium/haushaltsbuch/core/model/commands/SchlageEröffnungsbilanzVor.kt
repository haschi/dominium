package com.github.haschi.dominium.haushaltsbuch.core.model.commands

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class SchlageEröffnungsbilanzVor(
        @TargetAggregateIdentifier val bilanzId: Aggregatkennung,
        val inventurId: Aggregatkennung,
        val inventar: Inventar)
