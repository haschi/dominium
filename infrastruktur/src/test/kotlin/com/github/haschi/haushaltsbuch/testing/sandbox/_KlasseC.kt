package com.github.haschi.haushaltsbuch.testing.sandbox

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue

data class KlasseC @JsonCreator constructor(@get:JsonIgnore private val b: Boolean)
{
    @JsonValue fun value() = b;
}
