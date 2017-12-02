package org.github.haschi.haushaltsbuch.api.events

import org.github.haschi.haushaltsbuch.core.Eröffnungsbilanzkonto

data class EröffnungsbilanzkontoErstellt(
        val eröffnungsbilanzkonto: Eröffnungsbilanzkonto)
