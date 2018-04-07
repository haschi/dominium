package com.github.haschi.dominium.haushaltsbuch.core.domain.bilanz

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Eröffnungsbilanz
import org.slf4j.LoggerFactory

class BilanzAblage
{
    val index: MutableMap<Aggregatkennung, Eröffnungsbilanz> = mutableMapOf()

    fun speichern(inventarId: Aggregatkennung, bilanz: Eröffnungsbilanz)
    {
        logger.info("Eröffnungsbilanz speichern. ID=$inventarId")
        index[inventarId] = bilanz;
    }

    fun lesen(inventurId: Aggregatkennung): Eröffnungsbilanz
    {
        logger.info("Eröffnungsbilanz lesen. ID=$inventurId")
        try
        {
            return index[inventurId]!!
        } catch (ausnahme: Exception)
        {
            logger.error("Eröffnungsbilanz lesen fehlgeschlagen. ID=$inventurId")
            throw ausnahme
        }
    }

    companion object
    {
        val logger = LoggerFactory.getLogger(BilanzAblage::class.java)
    }
}