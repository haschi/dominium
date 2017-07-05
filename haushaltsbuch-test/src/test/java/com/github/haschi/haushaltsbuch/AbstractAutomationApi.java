package com.github.haschi.haushaltsbuch;

import org.picocontainer.Startable;

import java.util.function.Consumer;

public interface AbstractAutomationApi extends Startable
{
    public void haushaltsbuchführung(Consumer<AbstractHaushaltsbuchführungSteps> consumer);
}
