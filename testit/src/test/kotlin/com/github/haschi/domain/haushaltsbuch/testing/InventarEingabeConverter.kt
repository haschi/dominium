package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurGruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Kategorie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import cucumber.deps.com.thoughtworks.xstream.converters.Converter
import cucumber.deps.com.thoughtworks.xstream.converters.MarshallingContext
import cucumber.deps.com.thoughtworks.xstream.converters.UnmarshallingContext
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamReader
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamWriter

class InventarEingabeConverter : Converter
{
    override fun canConvert(p0: Class<*>?): Boolean
    {
        return  p0 == InventarEingabe::class.java
    }

    override fun unmarshal(reader: HierarchicalStreamReader, p1: UnmarshallingContext?): Any
    {
        val gruppe2 = nächsterWert(reader)
        val gruppe = InventurGruppe.valueOf(gruppe2)
        val kategorie = (nächsterWert(reader))

        return InventarEingabe(
                gruppe,
                gruppe2,
                Kategorie(kategorie),
                nächsterWert(reader),
                Währungsbetrag.währungsbetrag(nächsterWert(reader)))
    }

    private fun nächsterWert(reader: HierarchicalStreamReader): String
    {
        reader.moveDown()
        val wert = reader.value
        reader.moveUp()
        return wert
    }

    override fun marshal(p0: Any?, p1: HierarchicalStreamWriter?, p2: MarshallingContext?)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


