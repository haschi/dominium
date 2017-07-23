package com.github.haschi.haushaltsbuch;

import java.util.function.Consumer;

public interface AbstractHaushaltsbuchführungSteps
{
    void beginnen();

    void hauptbuchAngelegt();

    void aktuellesHauptbuch(Consumer<AbstractHauptbuchSteps> consumer);

    void journal(Consumer<AbstractJournalSteps> consumer);

    void inventar(Consumer<AbstractInventarSteps> consumer);

    InventarZustand inventar();

    void eröffnungsbilanz(Consumer<AbstractEröffnungsbilanzSteps> consumer);
}
