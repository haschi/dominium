package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DisplayName("JSON Serialisierung der Anweisung beginne Inventur")
@JsonTest
class BeginneInventurTest
{
    @Autowired
    private lateinit var json: JacksonTester<BeginneInventur>

    @Test
    fun `Serialisierung des Ereignisses prüfen`()
    {
        val command = BeginneInventur(Aggregatkennung.neu())

        Assertions.assertThat(this.json.write(command))
                .isEqualToJson("""{"id" : "${command.id.id}"}""")
    }
}