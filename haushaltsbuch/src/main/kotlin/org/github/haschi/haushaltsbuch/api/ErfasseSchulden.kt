package org.github.haschi.haushaltsbuch.api

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

data class ErfasseSchulden(
        @TargetAggregateIdentifier
        val inventurkennung: Aggregatkennung,
        val position: String,
        val währungsbetrag: Währungsbetrag)