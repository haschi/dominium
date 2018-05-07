package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.haschi.dominium.haushaltsbuch.core.domain.inventur.Ansicht
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurGruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import org.springframework.stereotype.Service

@Service
class HaushaltsbuchModule : SimpleModule()
{
    init
    {
        addSerializer(Aggregatkennung::class.java, AggregatkennungSerializer())
        addDeserializer(Aggregatkennung::class.java, AggregatkennungDeserializer())

        addSerializer(Währungsbetrag::class.java, WaehrungsbetragSerializer())
        addDeserializer(Währungsbetrag::class.java, WährungsbetragDeserializer())

        setMixInAnnotation(Inventar::class.java, InventarMixin::class.java)
        setMixInAnnotation(InventurGruppe::class.java, AnsichtMixin::class.java)
    }
}