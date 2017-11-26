package com.github.haschi.haushaltsbuch.api

import org.assertj.core.api.Assertions.assertThat
import org.github.haschi.haushaltsbuch.api.Währungsbetrag
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Währungsbetrag addieren")
class WährungsbetragAddierenTest
{

    @Test
    fun währungsbetrag_addieren()
    {

        val x = Währungsbetrag.euro(123.45)
        val y = Währungsbetrag.euro(1.0)

        val summe: Währungsbetrag = x + y

        assertThat(summe).isEqualTo(Währungsbetrag.euro(124.45))
    }
}