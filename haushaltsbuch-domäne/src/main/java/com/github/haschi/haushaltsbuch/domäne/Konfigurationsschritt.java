package com.github.haschi.haushaltsbuch.domäne;

import org.axonframework.config.Configurer;

public interface Konfigurationsschritt
{
    Configurer konfigurieren() throws Exception;
}
