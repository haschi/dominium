package com.github.haschi.haushaltsbuch.backend.rest.api

import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(CommandEndpoint::class)
class CommandEndpointTest
{
    @Autowired
    private lateinit var mock: MockMvc

    @MockBean
    private lateinit var gateway: CommandGateway

    @Test
    fun `post deserialisert commands`()
    {
        this.mock.perform(
                MockMvcRequestBuilders
                        .get("/gateway/command")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(418))
    }
}