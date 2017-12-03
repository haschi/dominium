package com.github.haschi.domain.haushaltsbuch

import org.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung

class DieWelt {
    var aktuelleInventur: Aggregatkennung? = null
    var intention: (() -> Unit)? = null
    var aktuellesHaushaltsbuch: Aggregatkennung? = null
}
