package com.github.haschi.haushaltsbuch.infrastruktur

import cucumber.runtime.java.picocontainer.PicoFactory

class SystemtestPicoFactory : PicoFactory()
{
    init
    {
        addClass(Testcloud::class.java)
        addClass(Welt::class.java)
    }
}
