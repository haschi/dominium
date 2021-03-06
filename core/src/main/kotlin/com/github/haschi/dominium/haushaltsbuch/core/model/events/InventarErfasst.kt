package com.github.haschi.dominium.haushaltsbuch.core.model.events

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Vermoegenswerte

data class InventarErfasst(
        val anlagevermoegen: Vermoegenswerte,
        val umlaufvermoegen: Vermoegenswerte,
        val schulden: Vermoegenswerte)
