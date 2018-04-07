package com.github.haschi.dominium.haushaltsbuch.core.model.events

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz

data class PrivateEröffnungsbilanzVorgeschlagen
(
    val bilanzId: Aggregatkennung,
    val inventurId: Aggregatkennung,
    val bilanz: Eröffnungsbilanz
)
