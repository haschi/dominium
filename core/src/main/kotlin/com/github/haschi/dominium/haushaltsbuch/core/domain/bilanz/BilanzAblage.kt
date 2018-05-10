package com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz

class BilanzAblage
{
    val index: MutableMap<Aggregatkennung, Eröffnungsbilanz> = mutableMapOf()

    fun speichern(inventarId: Aggregatkennung, bilanz: Eröffnungsbilanz)
    {
        index[inventarId] = bilanz
    }

    fun lesen(inventurId: Aggregatkennung): Eröffnungsbilanz
    {
        return index[inventurId]!!
    }
}