package com.github.haschi.haushaltsbuch.infrastruktur

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value=["reinvermoegen"], allowGetters=true) abstract class InventarMixin
{
}