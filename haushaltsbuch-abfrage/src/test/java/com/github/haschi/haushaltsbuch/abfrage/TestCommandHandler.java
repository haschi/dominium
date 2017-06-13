package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import org.axonframework.commandhandling.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCommandHandler
{
    Logger log = LoggerFactory.getLogger(TestCommandHandler.class);
    @CommandHandler
    public void legeHaushaltsbuchAn(final ImmutableBeginneHaushaltsbuchführung anweisung) {
        log.info("Haushaltsbuch wird angelegt.");
    }
}
