package com.github.haschi.haushaltsbuch;

import java.util.function.Consumer;

public interface AbstractHaushaltsbuchführungSteps
{
    void beginnen();

    void aktuellesHaushaltsbuch(Consumer<AbstractAktuellesHaushaltsbuchSteps> consumer);
}
