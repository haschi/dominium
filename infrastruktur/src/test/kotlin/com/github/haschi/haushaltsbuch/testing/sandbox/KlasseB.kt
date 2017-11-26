package com.github.haschi.haushaltsbuch.testing.sandbox

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue

data class KlasseB @JsonCreator constructor(@JsonIgnore private val i: Int)
{
    @JsonValue
    fun value() = i;
}
