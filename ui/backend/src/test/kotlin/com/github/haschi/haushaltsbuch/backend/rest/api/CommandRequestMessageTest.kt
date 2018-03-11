package com.github.haschi.haushaltsbuch.backend.rest.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit.jupiter.SpringExtension

data class Name(val vorname: String, val nachname: String, val alter: Int)
data class Person(val id: Int, val name: Name)

@ExtendWith(SpringExtension::class)
@JsonTest
class CommandRequestMessageTest
{
    @Autowired
    private lateinit var json: JacksonTester<RestCommandMessage>

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun `Rest Command Message kann leeren Payload deserialisieren`()
    {
        val content = json.parse("""{
                                |   "type" : "${Person::class.qualifiedName}",
                                |   "payload" : {
                                |       "id" : 12345,
                                |       "name" : {
                                |           "vorname" :"Matthias",
                                |           "nachname": "Haschka",
                                |           "alter": 99
                                |       }
                                |   },
                                |   "meta" : {}}""".trimMargin())
        println(content)

        val commandType = Class.forName(content.`object`.type)
        val person = mapper.convertValue(content.`object`.payload, commandType)

        assertThat(person).isEqualTo(Person(12345, Name("Matthias", "Haschka", 99)))
    }
}