package com.github.haschi.haushaltsbuch.abfrage;

import org.axonframework.config.Configuration;

public interface Systemumgebung
{
    Configuration konfigurieren() throws Exception;
}
