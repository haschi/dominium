package com.github.haschi.dominium.haushaltsbuch.core.model.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

data class ErfasseSchulden(
        @TargetAggregateIdentifier
        val inventurkennung: Aggregatkennung,
        val position: String,
        val währungsbetrag: Währungsbetrag)
