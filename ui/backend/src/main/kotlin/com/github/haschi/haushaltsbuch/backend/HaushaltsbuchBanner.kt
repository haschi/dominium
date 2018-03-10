package com.github.haschi.haushaltsbuch.backend

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.Banner
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import java.io.PrintStream
import java.io.PrintWriter

class HaushaltsbuchBanner : Banner
{
    override fun printBanner(environment: Environment?, sourceClass: Class<*>?, out: PrintStream?)
    {
        val writer = PrintWriter(out);

        val name = environment!!["haushaltsbuch.name"] ?: "Unbekannt"
        val version = environment!!["haushaltsbuch.version"] ?: "Unbekannt"
        val timestamp = environment!!["haushaltsbuch.timestamp"] ?: "Unbekannt"

        writer.println("$name ($version/$timestamp)")
        writer.flush()
    }
}