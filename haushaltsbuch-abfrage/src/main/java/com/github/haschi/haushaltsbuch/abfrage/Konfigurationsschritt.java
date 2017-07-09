package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configurer;

public interface Konfigurationsschritt
{
    Configurer konfigurieren() throws Exception;
}
