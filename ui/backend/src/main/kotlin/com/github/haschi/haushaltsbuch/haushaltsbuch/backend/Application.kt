package com.github.haschi.haushaltsbuch.haushaltsbuch.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
