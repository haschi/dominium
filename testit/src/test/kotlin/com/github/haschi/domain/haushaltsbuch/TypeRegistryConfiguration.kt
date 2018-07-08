package com.github.haschi.domain.haushaltsbuch

import com.github.haschi.domain.haushaltsbuch.testing.Bilanzposition
import com.github.haschi.domain.haushaltsbuch.testing.InventarEingabe
import com.github.haschi.domain.haushaltsbuch.testing.Kontozeile
import com.github.haschi.domain.haushaltsbuch.testing.VermögenswertParameter
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Buchung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.InventurGruppe
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Kategorie
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Währungsbetrag
import com.github.haschi.dominium.haushaltsbuch.core.model.values.euro
import cucumber.api.TypeRegistry
import cucumber.api.TypeRegistryConfigurer
import io.cucumber.cucumberexpressions.ParameterType
import io.cucumber.cucumberexpressions.Transformer
import io.cucumber.datatable.DataTableType
import io.cucumber.datatable.TableEntryTransformer
import io.cucumber.datatable.TableRowTransformer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.Locale.GERMAN
import java.util.regex.Pattern

class TypeRegistryConfiguration : TypeRegistryConfigurer
{
    val moneyRegexp = "(-?(?:\\d{1,3}\\.)?\\d{1,3},\\d{2} EUR)"

    override fun configureTypeRegistry(registry: TypeRegistry)
    {
        registry.defineParameterType(ParameterType(
                "zeitpunkt",
                """(\d{2}\.\d{2}\.\d{4} um \d{2}:\d{2})""",
                LocalDateTime::class.java,
                Transformer {
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'um' HH:mm")
                    LocalDateTime.parse(it, formatter)
                }))

        registry.defineParameterType(ParameterType(
                "währungsbetrag",
                moneyRegexp,
                Währungsbetrag::class.java,
                Transformer {
                    if (it.isEmpty())
                        0.0.euro()
                    Währungsbetrag.währungsbetrag(it)
                }))

        registry.defineDataTableType(DataTableType(
            Kontozeile::class.java,
                TableRowTransformer {
                    Kontozeile(Buchung.parse(it[0]), Buchung.parse(it[1]))
                }))

        registry.defineDataTableType(DataTableType(
                InventarEingabe::class.java,
                TableEntryTransformer {
                    InventarEingabe(
                            InventurGruppe.valueOf(it["Gruppe"]!!),
                            it["Gruppe"]!!,
                            Kategorie(it["Kategorie"]!!),
                            it["Position"]!!,
                            Währungsbetrag.währungsbetrag(it["Währungsbetrag"]!!))
                }))

        registry.defineDataTableType(DataTableType(
                VermögenswertParameter::class.java,
                TableEntryTransformer {
                    VermögenswertParameter(
                            it["Kategorie"]!!,
                            it["Position"]!!,
                            Währungsbetrag.währungsbetrag(it["Währungsbetrag"]!!))
                }))

        registry.defineDataTableType(DataTableType(
                        InventurStepDefinition.SchuldParameter::class.java,
                        TableEntryTransformer {
                            InventurStepDefinition.SchuldParameter(
                                    it["Kategorie"]!!,
                                    it["Position"]!!,
                                    Währungsbetrag.währungsbetrag(it["Währungsbetrag"]!!)
                            )
                        }))

        registry.defineDataTableType(DataTableType(
                Bilanzposition::class.java,
                TableEntryTransformer {
                    Bilanzposition(
                            seite = it["Seite"]!!,
                            gruppe = it["Gruppe"]!!,
                            kategorie = it["Kategorie"]!!,
                            posten = it["Posten"]!!,
                            betrag = Währungsbetrag.währungsbetrag(it["Währungsbetrag"]!!)
                    )
                }
        ))
    }

    fun Buchung.Companion.parse(buchung: String): Buchung
    {
                val pattern = Pattern.compile(moneyRegexp)
        val matcher = pattern.matcher(buchung)

        assert(matcher.matches()) { "Keine Übereinstimmung: $buchung" }

        return if (matcher.matches())
        {
            Buchung(
                    buchungstext = matcher.group(1),
                    betrag = Währungsbetrag.währungsbetrag(matcher.group(2)))
        } else
        {
            Buchung.leer
        }
    }

    override fun locale(): Locale
    {
        return GERMAN
    }
}