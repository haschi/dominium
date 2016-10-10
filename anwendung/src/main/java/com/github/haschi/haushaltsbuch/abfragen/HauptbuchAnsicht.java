package com.github.haschi.haushaltsbuch.abfragen;

import com.github.haschi.modeling.de.Information;

import java.util.List;
import java.util.UUID;

@Information
public interface HauptbuchAnsicht
{

    UUID haushaltsbuchId();

    List<String> aktivkonten();

    List<String> passivkonten();

    List<String> ertragskonten();

    List<String> aufwandskonten();
}
