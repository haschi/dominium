package com.github.haschi.haushaltsbuch.api

import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendung
import com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur
import com.github.haschi.dominium.haushaltsbuch.core.model.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.haushaltsbuch.infrastruktur.AxonInfrastrukturFactory
import io.reactivex.Observable
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture

class CommandGateayTest
{
    @Test
    fun kann_dominium_gateway_erstellen() {
        val infrastruktur = AxonInfrastrukturFactory()
        val anwendungskonfiguration = Anwendungskonfiguration(infrastruktur)

        val anwendung = anwendungskonfiguration.start { Anwendung(it) }

        val api = anwendung.api()
        api.inventur.send(BeginneInventur(Aggregatkennung.neu()))
                .observable()
                .subscribe({a -> println(a)}, {e->println(e)})

        anwendung.stop()
    }
}

private fun <T> CompletableFuture<T>.observable(): Observable<T>
{
    return Observable.fromFuture(this)
}
