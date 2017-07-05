package com.github.haschi.haushaltsbuch;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matthias on 05.07.17.
 */
public class ClassFinderTest
{
    @Test
    public void custom_pico_factory_existiert() throws ClassNotFoundException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Class<?> aClass = classLoader.loadClass("com.github.haschi.haushaltsbuch.infrastruktur.CustomPicoFactory");

        assertThat(aClass).isNotNull();
    }
}
