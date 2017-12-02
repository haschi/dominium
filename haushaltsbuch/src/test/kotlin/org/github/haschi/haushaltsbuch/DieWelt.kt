package org.github.haschi.haushaltsbuch

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

class DieWelt {
    var aktuelleInventur: Aggregatkennung? = null
    var intention: (() -> Unit)? = null
    var aktuellesHaushaltsbuch: Aggregatkennung? = null
}
