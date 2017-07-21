package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;

import java.util.function.Consumer;

public interface AbstractHaushaltsbuchführungSteps
{
    void beginnen();

    void hauptbuchAngelegt();

    void aktuellesHauptbuch(Consumer<AbstractHauptbuchSteps> consumer);

    void journalAngelegt(Aggregatkennung uuid);

    void journal(Consumer<AbstractJournalSteps> consumer);
    // void hauptbuch(Consumer<AbstractHauptbuchSteps> consumer);
}
