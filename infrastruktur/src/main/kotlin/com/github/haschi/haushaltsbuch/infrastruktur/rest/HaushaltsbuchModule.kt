package com.github.haschi.haushaltsbuch.infrastruktur.rest

import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag

class HaushaltsbuchModule : SimpleModule()
{
    init
    {
        addSerializer(Aggregatkennung::class.java, AggregatkennungSerialisierer())
        addDeserializer(Aggregatkennung::class.java, AggregatkennungDeserializer())

        addSerializer(Währungsbetrag::class.java, WährungsbetragSerialisierer())
        addDeserializer(Währungsbetrag::class.java, WährungsbetragDeserialisierer())

        setMixInAnnotation(Inventar::class.java, InventarMixin::class.java)
    }
}