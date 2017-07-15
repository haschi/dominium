package com.github.haschi.haushaltsbuch.abfragen;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.haschi.haushaltsbuch.api.Aggregatkennung;
import com.github.haschi.modeling.de.Information;

import java.util.List;

@Information
@JsonSerialize(as = ImmutableHauptbuchAnsicht.class)
public interface HauptbuchAnsicht
{
    Aggregatkennung haushaltsbuchId();

    List<String> aktivkonten();

    List<String> passivkonten();

    List<String> ertragskonten();

    List<String> aufwandskonten();
}
