package com.github.haschi.haushaltsbuch.testing.sandbox
import com.fasterxml.jackson.annotation.*

//data class KlasseA @JsonCreator constructor(@JsonIgnore val s: String) {
//    @JsonValue override fun toString() = s
//}

data class KlasseA @JsonCreator constructor(@get:JsonIgnore val s: String) {
    @JsonValue fun value() = s;
}