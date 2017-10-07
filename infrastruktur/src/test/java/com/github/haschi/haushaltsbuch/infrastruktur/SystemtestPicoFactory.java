package com.github.haschi.haushaltsbuch.infrastruktur;

import cucumber.runtime.java.picocontainer.PicoFactory;

public class SystemtestPicoFactory extends PicoFactory
{
    public SystemtestPicoFactory()
    {
        addClass(Testcloud.class);
        addClass(Welt.class);
    }
}
