package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;

import java.util.UUID;

public interface AutomationApi
{
    void start() throws Exception;

    void stop();

    void haushaltsführungBegonnen(
            AggregateProxy<HaushaltsbuchTestaggregat> aggregat,
            ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt);

    Haushaltsbuch haushaltsbuch(UUID identifier);

    String requiredTag();

    void werdeIchEinHaushaltsbuchSehen(UUID identifier, ImmutableHaushaltsbuch leeresHaushaltsbuch);

    void werdeIchKeinHaushaltsbuchSehen(UUID identifier);
}
