package com.github.haschi.haushaltsbuch.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContext

@SpringBootApplication
class Application

private var application: ApplicationContext? = null

fun main(args: Array<String>) {
    application = SpringApplicationBuilder(Application::class.java)
            .banner(HaushaltsbuchBanner())
            .run(*args)
}

fun exit()
{
    if (application != null)
    {
        SpringApplication.exit(application);
    }
}
