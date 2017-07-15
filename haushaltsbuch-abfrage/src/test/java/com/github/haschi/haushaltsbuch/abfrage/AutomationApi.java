package com.github.haschi.haushaltsbuch.abfrage;

import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.haushaltsbuch.api.ImmutableHaushaltsbuchAngelegt;

public interface AutomationApi
{
    void start() throws Exception;

    void stop();

    void haushaltsführungBegonnen(
            AggregateProxy<HaushaltsbuchTestaggregat> aggregat,
            ImmutableHaushaltsbuchAngelegt haushaltsbuchAngelegt);

    Haushaltsbuch haushaltsbuch(Aggregatkennung identifier);

    String requiredTag();

    void werdeIchEinHaushaltsbuchSehen(Aggregatkennung identifier, ImmutableHaushaltsbuch leeresHaushaltsbuch);

    void werdeIchKeinHaushaltsbuchSehen(Aggregatkennung identifier);
}
