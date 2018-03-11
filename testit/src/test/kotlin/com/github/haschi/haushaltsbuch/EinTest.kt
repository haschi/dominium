package com.github.haschi.haushaltsbuch

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.Test

class EinTest {
    @Test
    fun ein_test()
    {
        assertThat(1).isCloseTo(2, Offset.offset(1))
    }
}
