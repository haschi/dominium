package com.github.haschi.haushaltsbuch.backend.rest.api

import com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.VersionEndpoint
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(
        basePackages = arrayOf("com.github.haschi.haushaltsbuch"),
        basePackageClasses = arrayOf(VersionEndpoint::class))
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
