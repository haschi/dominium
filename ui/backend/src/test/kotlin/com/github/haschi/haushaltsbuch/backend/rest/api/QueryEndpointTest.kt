package com.github.haschi.haushaltsbuch.backend.rest.api

import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar
import com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseVersion
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Version
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

@ExtendWith(SpringExtension::class)
@WebMvcTest(QueryEndpoint::class)
class QueryEndpointTest
{
    data class Testfall(
            val titel: String,
            val json: String,
            val query: Any,
            val resultType: KClass<*>)

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var gateway: QueryGateway

    @TestFactory
    fun `post ruft query gateway auf`(): List<DynamicTest>
    {
        return listOf(
                Testfall(
                        titel = "LeseInventar",
                        json = """
                            |{
                            |  "type" : "com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar",
                            |  "payload" : { "id" : { "id" : "673dc099-7d89-4f00-a997-3434ba5af685" } },
                            |  "result" : "com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar"
                            |}""".trimMargin(),
                        query = LeseInventar(Aggregatkennung.aus("673dc099-7d89-4f00-a997-3434ba5af685")),
                        resultType = Inventar::class),
                Testfall(
                        titel = "LeseVersion",
                        json = """
                            |{
                            |   "type" : "com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseVersion",
                            |   "payload" : {},
                            |   "result" : "com.github.haschi.dominium.haushaltsbuch.core.model.values.Version"
                            |}""".trimMargin(),
                        query = LeseVersion(),
                        resultType = Version::class
                ))
                .map {
                    dynamicTest(it.titel, {
                        val future = mock(CompletableFuture::class.java)
                        given(gateway.query(it.query, it.resultType.java)).willReturn(future)
                        given(future.get()).willReturn(Version("haushaltsbuch", "123"))
                        this.mvc.perform(
                                MockMvcRequestBuilders
                                        .post("/gateway/query")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(it.json))
                                .andExpect(MockMvcResultMatchers.status().`is`(200))

                        verify(gateway).query(it.query, it.resultType.java)
                        verify(future).get()
                    })
                }
    }

    data class TestfallFürStatus400(
            val titel: String,
            val content: String,
            val fehlermeldung: String)

    @TestFactory
    fun status400(): List<DynamicTest>
    {
        return listOf(
                TestfallFürStatus400(titel = "Unbekanntes Kommando",
                        content = """
                            |{
                            |   "type" : "com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseVersionX",
                            |   "payload" : {},
                            |   "result" : "com.github.haschi.dominium.haushaltsbuch.core.model.values.Version"
                            |}""".trimMargin(),
                        fehlermeldung = "Unbekanntes Kommando"),
                TestfallFürStatus400(
                        titel = "Unbekannter Ergebnis-Typ",
                        content = """
                            |{
                            |   "type" : "com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseVersion",
                            |   "payload" : {},
                            |   "result" : "com.github.haschi.dominium.haushaltsbuch.core.model.values.VersionX"
                            |}""".trimMargin(),
                        fehlermeldung = "Unbekannter Ergebnis-Typ"),
                TestfallFürStatus400(
                        titel = "Fehler bei der Deserialisierung der Anfrage",
                        content = """
                            |{
                            |  "type" : "com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar",
                            |  "payload" : { },
                            |  "result" : "com.github.haschi.dominium.haushaltsbuch.core.model.values.Inventar"
                            |}""".trimMargin(),
                        fehlermeldung = "Fehler bei der Deserialisierung der Anfrage"))
                .map {
                    dynamicTest(it.titel, {
                        this.mvc.perform(
                                MockMvcRequestBuilders
                                        .post("/gateway/query")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(it.content))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                                .andExpect(MockMvcResultMatchers.content().string(it.fehlermeldung))
                    })
                }
    }
}

