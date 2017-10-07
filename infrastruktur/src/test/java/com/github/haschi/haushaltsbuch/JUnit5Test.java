package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.infrastruktur.RestApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JUnit5 Test")
public class JUnit5Test
{
    @Test
    @DisplayName("Prüfe, ob JUnit4 und JUnit5 zusammen ausgeführt werden können")
    public void testJunit5() throws IOException
    {
        assertThat(RestApi.getServiceProperty("service.version")).isEqualTo("0.0.1-SNAPSHOT");
    }
}
