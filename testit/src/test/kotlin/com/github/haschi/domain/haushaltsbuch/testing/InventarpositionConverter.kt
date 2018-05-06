package com.github.haschi.domain.haushaltsbuch.testing

import com.github.haschi.dominium.haushaltsbuch.core.model.values.Kategorie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import cucumber.deps.com.thoughtworks.xstream.converters.Converter
import cucumber.deps.com.thoughtworks.xstream.converters.MarshallingContext
import cucumber.deps.com.thoughtworks.xstream.converters.UnmarshallingContext
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamReader
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamWriter

class InventarpositionConverter : Converter
{
    override fun canConvert(p0: Class<*>?): Boolean
    {
        return  p0 == Inventarposition::class.java
    }

    override fun unmarshal(reader: HierarchicalStreamReader, p1: UnmarshallingContext?): Any
    {
        val gruppe = Inventurgruppen.valueOf(nächsterWert(reader)).gruppe

        return Inventarposition(
                gruppe,
                Kategorie(nächsterWert(reader)),
                nächsterWert(reader),
                Währungsbetrag.währungsbetrag(nächsterWert(reader)))
    }

    private fun nächsterWert(reader: HierarchicalStreamReader): String
    {
        reader.moveDown();
        val wert = reader.value
        reader.moveUp()
        return wert
    }

    override fun marshal(p0: Any?, p1: HierarchicalStreamWriter?, p2: MarshallingContext?)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class Inventurgruppe(val bezeichnung: String, val kategorien: Array<Kategorie>) : Comparable<Inventurgruppe>
{
    override fun compareTo(other: Inventurgruppe): Int
    {
        return bezeichnung.compareTo(other.bezeichnung)
    }
}

enum class Inventurgruppen(var gruppe: Inventurgruppe)
{
    Anlagevermögen(Inventurgruppe("Anlagevermögen", arrayOf(Kategorie("Sonstiges")))),
    Umlaufvermögen(Inventurgruppe("Umlaufvermögen", arrayOf(Kategorie("Sonstiges")))),
    Schulden(Inventurgruppe("Schulden", arrayOf(Kategorie("Sonstiges")))),
}

