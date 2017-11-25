package com.github.haschi.haushaltsbuch.testing.sandbox

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize
data class AussereKlasse @JsonCreator constructor(
        val a: KlasseA,
        val b: KlasseB,
        val c: KlasseC,
        val d: KlasseD
)
