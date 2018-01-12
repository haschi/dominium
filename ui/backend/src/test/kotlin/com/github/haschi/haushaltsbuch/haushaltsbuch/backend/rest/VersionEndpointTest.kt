package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(VersionEndpoint::class)
class VersionEndpointTest
{
    @Autowired
    private lateinit var mock: MockMvc

    @Test
    fun `get liefert Version`()
    {
        this.mock.perform(MockMvcRequestBuilders.get("/version").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk)
    }
}