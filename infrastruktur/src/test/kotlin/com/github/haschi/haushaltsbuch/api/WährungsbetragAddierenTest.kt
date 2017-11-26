package com.github.haschi.haushaltsbuch.api

import org.assertj.core.api.Assertions.assertThat
import org.github.haschi.haushaltsbuch.api.Währungsbetrag
import org.github.haschi.haushaltsbuch.api.euro
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

@DisplayName("Währungsbetrag addieren")
class WährungsbetragAddierenTest
{
    @TestFactory
    fun addieren(): List<DynamicNode>
    {
        data class Testfall(
                val x: Währungsbetrag,
                val y: Währungsbetrag,
                val summe: Währungsbetrag
        )

        return listOf(
                Testfall(
                        0.0.euro(),
                        Währungsbetrag.NullEuro,
                        Währungsbetrag.NullEuro),
                Testfall(
                        Währungsbetrag.euro(123.45),
                        Währungsbetrag.NullEuro,
                        Währungsbetrag.euro(123.45)),
                Testfall(
                        Währungsbetrag.NullEuro,
                        Währungsbetrag.euro(123.45),
                        Währungsbetrag.euro(123.45)),
                Testfall(
                        Währungsbetrag.euro(42.0),
                        Währungsbetrag.euro(7.0),
                        Währungsbetrag.euro(49.0))
        ).map { testfall ->
            dynamicTest(
                    "${testfall.x} + ${testfall.y}",
                    {
                        assertThat(testfall.x + testfall.y)
                                .isEqualTo(testfall.summe)
                    })
        }
    }

}