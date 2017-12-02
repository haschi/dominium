package com.github.haschi.haushaltsbuch.testing.sandbox

import nl.jqno.equalsverifier.EqualsVerifier
import org.github.haschi.haushaltsbuch.api.events.BeendeInventur
import org.github.haschi.haushaltsbuch.api.commands.BeginneHaushaltsbuchführung
import org.github.haschi.haushaltsbuch.api.commands.BeginneInventur
import org.github.haschi.haushaltsbuch.api.commands.ErfasseInventar
import org.github.haschi.haushaltsbuch.api.commands.ErfasseSchulden
import org.github.haschi.haushaltsbuch.api.commands.ErfasseUmlaufvermögen
import org.github.haschi.haushaltsbuch.api.events.EröffnungsbilanzkontoErstellt
import org.github.haschi.haushaltsbuch.api.events.HaushaltsbuchführungBegonnen
import org.github.haschi.haushaltsbuch.api.events.InventarErfasst
import org.github.haschi.haushaltsbuch.api.events.InventurBegonnen
import org.github.haschi.haushaltsbuch.api.events.SchuldErfasst
import org.github.haschi.haushaltsbuch.api.events.UmlaufvermögenErfasst
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import java.util.stream.Stream
import kotlin.reflect.KClass

@DisplayName("Äquivalenzregeln der Wertobjekte prüfen")
class AequivalenzTest
{
    @TestTemplate
    @ExtendWith(DatenklassenProvider::class, AnweisungenAnbieter::class, EreignisAnbieter::class)
    @DisplayName("prüfe, ob Äquivalenzregeln für die Klasse eingehalten sind")
    fun aequivalenz(testfall: Testfall)
    {
        EqualsVerifier.forClass(testfall.testklasse.java).verify()
    }

    data class Testfall(val testklasse: KClass<*>)

    class DatenklassenProvider : TestfallAnbieter<Testfall>(Testfall::class)
    {

        override fun testfälle(): Stream<Testfall> = Stream.of(
                Testfall(KlasseA::class),
                Testfall(KlasseB::class),
                Testfall(KlasseC::class),
                Testfall(KlasseD::class),
                Testfall(KlasseE::class))
    }

    class AnweisungenAnbieter : TestfallAnbieter<Testfall>(Testfall::class)
    {
        override fun testfälle(): Stream<Testfall> = Stream.of(
                Testfall(BeendeInventur::class),
                Testfall(BeginneHaushaltsbuchführung::class),
                Testfall(BeginneInventur::class),
                Testfall(ErfasseInventar::class),
                Testfall(ErfasseSchulden::class),
                Testfall(ErfasseUmlaufvermögen::class))
    }

    class EreignisAnbieter: TestfallAnbieter<Testfall>(Testfall::class)
    {
        override  fun testfälle(): Stream<Testfall> = Stream.of(
                Testfall(EröffnungsbilanzkontoErstellt::class),
                Testfall(HaushaltsbuchführungBegonnen::class),
                Testfall(InventarErfasst::class),

                Testfall(InventurBegonnen::class),
                Testfall(SchuldErfasst::class),
                Testfall(UmlaufvermögenErfasst::class))
    }
}