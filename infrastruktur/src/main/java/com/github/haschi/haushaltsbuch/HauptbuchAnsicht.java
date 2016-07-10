package com.github.haschi.haushaltsbuch;

import org.immutables.value.Value;

import java.util.List;
import java.util.UUID;

@Value.Immutable
public interface HauptbuchAnsicht  {

    UUID haushaltsbuchId();

    List<String> aktivkonten();
    List<String> passivkonten();
    List<String> ertragskonten();
    List<String> aufwandskonten();
}
