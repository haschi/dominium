package com.github.haschi.dominium.haushaltsbuch.core.model.values

import org.assertj.core.api.AbstractThrowableAssert
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class EröffnungsbilanzTest
{
    data class Testfall(
            val beschreibung: String,
            val aktiva: Aktiva,
            val passiva: Passiva,
            val erwartet: (assertion: AbstractThrowableAssert<*, out Throwable>) -> Unit)

    class GültigeTestfälle : ArgumentsProvider
    {
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments>
        {
            return Stream.of(
                    Testfall(
                            "Bilanz ohne jegliche Einträge",
                            Aktiva(Vermoegenswerte(), Vermoegenswerte(), Vermoegenswerte()),
                            Passiva(Vermoegenswerte(), Vermoegenswerte()),
                            { it.doesNotThrowAnyException() }),
                    Testfall(
                            "Bilanz mit unterschiedlichen Summen",
                            Aktiva(
                                    Vermoegenswerte(
                                            Vermoegenswert("Eigentumswohnung", 12.00.euro())),
                                    Vermoegenswerte(), Vermoegenswerte()),
                            Passiva(Vermoegenswerte(), Vermoegenswerte()),
                            { it.isInstanceOf(BilanzsummenNichtIdentisch::class.java) }))
                    .map { Arguments.of(it.beschreibung, it) }
        }
    }

    @DisplayName("Kein Fehler beim Erstellen der Bilanz")
    @ParameterizedTest(name = "{index}. {0}")
    @ArgumentsSource(GültigeTestfälle::class)
    fun bilanzsummentest(beschreibung: String, testfall: Testfall)
    {
        val catched = catchThrowable { Eröffnungsbilanz(testfall.aktiva, testfall.passiva) }
        val assertion = assertThat(catched)
        testfall.erwartet(assertion)
    }
}