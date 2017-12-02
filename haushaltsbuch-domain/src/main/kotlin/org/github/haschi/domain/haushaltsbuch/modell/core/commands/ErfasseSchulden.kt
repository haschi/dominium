package org.github.haschi.domain.haushaltsbuch.modell.core.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import org.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung

data class ErfasseSchulden(
        @TargetAggregateIdentifier
        val inventurkennung: Aggregatkennung,
        val position: String,
        val währungsbetrag: Währungsbetrag)
