package com.github.haschi.haushaltsbuch

import io.restassured.RestAssured
import io.restassured.response.Response
import org.hamcrest.CoreMatchers.`is`

open class HaushaltsbuchDienstRestSteps
{
    private var response: Response? = null

    open fun wennIchDieVersionAbfrage()
    {
        response = RestAssured.`when`()
                .get("http://localhost:8080/gateway/version")

    }

    fun prüfeVersion(service: String, version: String)
    {
        response!!.then().statusCode(200)
                .body("version", `is` (version))
                .body("name", `is` (service))
    }
}
