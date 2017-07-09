package com.github.haschi.haushaltsbuch;

import org.picocontainer.Startable;

import java.util.function.Consumer;

public interface AbstractAutomationApi extends Startable
{
    void haushaltsbuchführung(Consumer<AbstractHaushaltsbuchführungSteps> consumer);
}
