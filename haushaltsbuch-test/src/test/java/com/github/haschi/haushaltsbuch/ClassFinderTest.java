package com.github.haschi.haushaltsbuch;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassFinderTest
{
    @Test
    public void custom_pico_factory_existiert() throws ClassNotFoundException
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Class<?> aClass = classLoader.loadClass("com.github.haschi.haushaltsbuch.infrastruktur.CustomPicoFactory");

        assertThat(aClass).isNotNull();
    }
}
