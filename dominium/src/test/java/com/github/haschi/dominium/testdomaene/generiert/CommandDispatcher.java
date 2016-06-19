package com.github.haschi.dominium.testdomaene.generiert;

import com.github.haschi.dominium.testdomaene.ÄndereZustand;

public interface CommandDispatcher {

    void ausführen(ÄndereZustand command);
}
