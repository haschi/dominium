package com.github.haschi.haushaltsbuch.testing.sandbox

import nl.jqno.equalsverifier.EqualsVerifier
import org.github.haschi.haushaltsbuch.api.BeendeInventur
import org.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung
import org.github.haschi.haushaltsbuch.api.BeginneInventur
import org.github.haschi.haushaltsbuch.api.ErfasseInventar
import org.github.haschi.haushaltsbuch.api.ErfasseSchulden
import org.github.haschi.haushaltsbuch.api.ErfasseUmlaufvermögen
import org.github.haschi.haushaltsbuch.api.EröffnungsbilanzkontoErstellt
import org.github.haschi.haushaltsbuch.api.HaushaltsbuchführungBegonnen
import org.github.haschi.haushaltsbuch.api.InventarErfasst
import org.github.haschi.haushaltsbuch.api.InventurBegonnen
import org.github.haschi.haushaltsbuch.api.SchuldErfasst
import org.github.haschi.haushaltsbuch.api.UmlaufvermögenErfasst
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
    fun aequivalenz(testklasse: KClass<*>)
    {
        EqualsVerifier.forClass(testklasse.java).verify()
    }

    class DatenklassenProvider : TestfallAnbieter<KClass<*>>()
    {
        override fun testfälle(): Stream<KClass<*>> = Stream.of(
                KlasseA::class,
                KlasseB::class,
                KlasseC::class,
                KlasseD::class,
                KlasseE::class)
    }

    class AnweisungenAnbieter : TestfallAnbieter<KClass<*>>()
    {
        override fun testfälle(): Stream<KClass<*>> = Stream.of(
                BeendeInventur::class,
                BeginneHaushaltsbuchführung::class,
                BeginneInventur::class,
                ErfasseInventar::class,
                ErfasseSchulden::class,
                ErfasseUmlaufvermögen::class)
    }

    class EreignisAnbieter: TestfallAnbieter<KClass<*>>()
    {
        override  fun testfälle(): Stream<KClass<*>> = Stream.of(
                EröffnungsbilanzkontoErstellt::class,
                HaushaltsbuchführungBegonnen::class,
                InventarErfasst::class,
                // InventurBeendet::class,
                InventurBegonnen::class,
                SchuldErfasst::class,
                UmlaufvermögenErfasst::class)
    }
}