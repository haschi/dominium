package com.github.haschi.haushaltsbuch.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext

@SpringBootApplication
class Application

private var application: ApplicationContext? = null

fun main(args: Array<String>) {
    application = SpringApplication.run(Application::class.java, *args)
}

fun exit()
{
    SpringApplication.exit(application);
}
