package com.github.haschi.dominium.haushaltsbuch.core.model.events

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import java.time.LocalDateTime

data class InventurBeendet(val inventurId: Aggregatkennung, val beendetAm: LocalDateTime)
