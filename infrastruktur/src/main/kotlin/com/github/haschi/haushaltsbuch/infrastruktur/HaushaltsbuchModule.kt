package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung

class HaushaltsbuchModule : SimpleModule()
{
    init
    {
        addSerializer(Aggregatkennung::class.java, AggregatkennungSerialisierer())
        addDeserializer(Aggregatkennung::class.java, AggregatkennungDeserializer())
    }
}