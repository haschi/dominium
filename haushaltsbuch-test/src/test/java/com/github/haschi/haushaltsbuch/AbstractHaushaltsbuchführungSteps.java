package com.github.haschi.haushaltsbuch;

import java.util.UUID;

public interface AbstractHaushaltsbuchführungSteps
{
    void beginnen();

    void hauptbuchAngelegt(UUID haushaltsbuch, UUID hauptbuch);

    UUID aktuellesHaushaltsbuch();

    UUID aktuellesHauptbuch();
}
