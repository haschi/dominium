package com.github.haschi.haushaltsbuch.api.refaktorisiert;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.haschi.modeling.de.Information;

@Information
@JsonDeserialize(as = ImmutableKonto.class)
@JsonSerialize(as = ImmutableKonto.class)
public interface Konto
{
    String nummer();
    String bezeichnung();
    Kontoart art();
}
