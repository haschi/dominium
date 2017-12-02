package org.github.haschi.haushaltsbuch.api.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.github.haschi.haushaltsbuch.core.Währungsbetrag
import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

data class ErfasseUmlaufvermögen(
        @TargetAggregateIdentifier val inventurkennung: Aggregatkennung,
        val position: String,
        val währungsbetrag: Währungsbetrag)