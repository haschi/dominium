package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.haushaltsbuch.backend.UnbekanntesKommando

class Query(
        val message: RestQueryMessage,
        val mapper: ObjectMapper)
{
    val query: Any
        get()
        {
            return try
            {
                mapper.convertValue(message.payload,
                        queryType)
            }
            catch (ausnahme: IllegalArgumentException)
            {
                throw FehlerBeiDerDeserialisierung()
            }
        }

    val resultType: Class<*>
        get()
        {
            return try
            {
                Class.forName(message.result)
            }
            catch (ausnahme: ClassNotFoundException)
            {
                throw UnbekannterErgebnisTyp()
            }
        }

    private val queryType: Class<*>
        get()
        {
            return try
            {
                Class.forName(message.type)
            }
            catch (ausnahme: ClassNotFoundException)
            {
                throw UnbekanntesKommando()
            }
        }
}