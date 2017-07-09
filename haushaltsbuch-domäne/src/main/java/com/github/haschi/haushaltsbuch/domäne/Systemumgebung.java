package com.github.haschi.haushaltsbuch.domäne;

import org.axonframework.config.Configuration;

public interface Systemumgebung
{
    Configuration konfigurieren() throws Exception;
}
