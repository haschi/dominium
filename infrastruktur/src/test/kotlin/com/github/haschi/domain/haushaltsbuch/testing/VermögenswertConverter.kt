package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.domain.haushaltsbuch.modell.core.values.Vermoegenswert
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Währungsbetrag
import cucumber.deps.com.thoughtworks.xstream.converters.Converter
import cucumber.deps.com.thoughtworks.xstream.converters.MarshallingContext
import cucumber.deps.com.thoughtworks.xstream.converters.UnmarshallingContext
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamReader
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamWriter


class VermögenswertConverter : Converter
{

    override fun canConvert(clazz: Class<*>): Boolean
    {
        return clazz == VermögenswertParameter::class.java
    }

    override fun marshal(value: Any, writer: HierarchicalStreamWriter,
                         context: MarshallingContext)
    {
        val person = value as Vermoegenswert

        writer.startNode("position")
        writer.setValue(person.position)
        writer.endNode()

        writer.startNode("betrag")
        writer.setValue(person.währungsbetrag.toString())
        writer.endNode()
    }

    override fun unmarshal(reader: HierarchicalStreamReader,
                           context: UnmarshallingContext): Any
    {
        reader.moveDown()
        val position = reader.getValue()
        reader.moveUp()
        reader.moveDown()
        val betrag = reader.value
        reader.moveUp()
        return VermögenswertParameter(position, Währungsbetrag.währungsbetrag(betrag))
    }

}