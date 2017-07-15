package com.github.haschi.haushaltsbuch;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;

public interface AbstractHaushaltsbuchführungSteps
{
    void beginnen();

    void hauptbuchAngelegt(Aggregatkennung haushaltsbuch, Aggregatkennung hauptbuch);

    Aggregatkennung aktuellesHaushaltsbuch();

    Aggregatkennung aktuellesHauptbuch();

    void journalAngelegt(Aggregatkennung uuid);
}
