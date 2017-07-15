package com.github.haschi.haushaltsbuch.domäne;

import org.axonframework.config.Configuration;

public class Testumgebung implements Systemumgebung
{
    @Override
    public Configuration konfigurieren() throws Exception
    {
        return null;
    }
}
