package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableBeginneHaushaltsbuchführung;
import org.axonframework.commandhandling.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class HaushaltsbuchAnlegenHandler
{
    Logger log = LoggerFactory.getLogger(HaushaltsbuchAnlegenHandler.class);
    @CommandHandler
    public void beginneHaushaltsbuchführung(final ImmutableBeginneHaushaltsbuchführung anweisung) {
        log.info(MessageFormat.format("Beginne Haushaltsbuchführung für {0}", anweisung.id()));
    }
}
