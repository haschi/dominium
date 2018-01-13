package com.github.haschi.haushaltsbuch.haushaltsbuch.backend

import com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.VersionEndpoint
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
