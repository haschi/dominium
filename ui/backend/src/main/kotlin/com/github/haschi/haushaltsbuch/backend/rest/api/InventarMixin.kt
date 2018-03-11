package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["reinvermoegen"], allowGetters = true)
abstract class InventarMixin
