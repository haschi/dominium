package com.github.haschi.haushaltsbuch

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import java.io.IOException

import org.assertj.core.api.Assertions.assertThat

@DisplayName("JUnit5 Test")
class JUnit5Test
{
    @Test
    @DisplayName("Prüfe, ob JUnit4 und JUnit5 zusammen ausgeführt werden können")
    @Throws(IOException::class)
    fun testJunit5()
    {
        assertThat(RestApi.getServiceProperty("service.version")).isEqualTo("CD-SNAPSHOT")
    }
}
