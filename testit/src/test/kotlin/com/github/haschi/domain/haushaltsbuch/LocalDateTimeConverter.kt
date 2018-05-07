package com.github.haschi.domain.haushaltsbuch

import cucumber.api.Transformer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



class LocalDateTimeConverter : Transformer<LocalDateTime>()
{
    override fun transform(datum: String?): LocalDateTime
    {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'um' HH:mm")
        return LocalDateTime.parse(datum, formatter)
    }
}
