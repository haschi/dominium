package org.github.haschi.haushaltsbuch

import org.github.haschi.haushaltsbuch.modell.core.values.Aggregatkennung

class DieWelt {
    var aktuelleInventur: Aggregatkennung? = null
    var intention: (() -> Unit)? = null
    var aktuellesHaushaltsbuch: Aggregatkennung? = null
}
