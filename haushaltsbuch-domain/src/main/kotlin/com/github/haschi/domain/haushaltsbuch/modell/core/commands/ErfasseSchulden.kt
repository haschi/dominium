package com.github.haschi.domain.haushaltsbuch.modell.core.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag

data class ErfasseSchulden(
        @TargetAggregateIdentifier
        val inventurkennung: Aggregatkennung,
        val position: String,
        val währungsbetrag: Währungsbetrag)
