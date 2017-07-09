package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.infrastruktur.CustomPicoFactory;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class CucumberObjectFactoryTest
{
    @Test
    public void cucumber_verwendet_vorgegebene_object_factory() throws IOException
    {
        final String filename = "cucumber.properties";

        try (InputStream input = CucumberObjectFactoryTest.class.getClassLoader().getResourceAsStream(filename))
        {
            final Properties properties = new Properties();
            properties.load(input);

            assertThat(properties.getProperty("cucumber.api.java.ObjectFactory"))
                    .isEqualTo(CustomPicoFactory.class.getCanonicalName());
        }
    }
}
