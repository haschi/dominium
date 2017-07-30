package com.github.haschi.haushaltsbuch.domäne;

import com.github.haschi.haushaltsbuch.api.refaktorisiert.ImmutableBefehleAnweisung;
import org.axonframework.commandhandling.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Befehlshandler
{
    Logger log = LoggerFactory.getLogger(Befehlshandler.class);

    @CommandHandler
    public void mach(final ImmutableBefehleAnweisung anweisung) {
        log.info("Ausgabe: " + anweisung.zahl());
    }
}
