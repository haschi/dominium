package com.github.haschi.haushaltsbuch.testing.sandbox

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneHaushaltsbuchführung
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseInventar
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseSchulden
import com.github.haschi.domain.haushaltsbuch.modell.core.commands.ErfasseUmlaufvermögen
import com.github.haschi.domain.haushaltsbuch.modell.core.events.BeendeInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.events.EröffnungsbilanzkontoErstellt
import com.github.haschi.domain.haushaltsbuch.modell.core.events.HaushaltsbuchführungBegonnen
import com.github.haschi.domain.haushaltsbuch.modell.core.events.InventarErfasst
import com.github.haschi.domain.haushaltsbuch.modell.core.events.InventurBegonnen
import com.github.haschi.domain.haushaltsbuch.modell.core.events.SchuldErfasst
import com.github.haschi.domain.haushaltsbuch.modell.core.events.UmlaufvermögenErfasst
import nl.jqno.equalsverifier.EqualsVerifier
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
    @DisplayName("prüfe, ob Äquivalenzregeln id die Klasse eingehalten sind")
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