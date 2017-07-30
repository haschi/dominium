package com.github.haschi.haushaltsbuch;

import java.util.function.Consumer;

public interface AbstractAktuellesHaushaltsbuchSteps
{
    void hauptbuch(Consumer<AbstractHauptbuchSteps> consumer);

    void inventar(StepBuilder builder);

    void inventar(StepConsumer consumer);

    void journal(Consumer<AbstractJournalSteps> consumer);

    void kontenrahmen(Consumer<AbstractKontenrahmenSteps> consumer);
}
