package com.github.haschi.haushaltsbuch;

import java.util.function.Consumer;

public interface AbstractAktuellesInventarSteps
{
    void eröffnungsbilanz(Consumer<AbstractEröffnungsbilanzSteps> consumer);
}
