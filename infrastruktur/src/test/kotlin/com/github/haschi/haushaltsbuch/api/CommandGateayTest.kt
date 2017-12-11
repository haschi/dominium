package com.github.haschi.haushaltsbuch.api

import com.github.haschi.domain.haushaltsbuch.modell.core.commands.BeginneInventur
import com.github.haschi.domain.haushaltsbuch.modell.core.values.Aggregatkennung
import com.github.haschi.dominium.haushaltsbuch.core.application.Anwendungskonfiguration
import com.github.haschi.haushaltsbuch.infrastruktur.AxonInfrastrukturFactory
import io.reactivex.Observable
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture

class CommandGateayTest
{
    @Test
    public fun kann_dominium_gateway_erstellen() {
        val infrastruktur = AxonInfrastrukturFactory()
        val anwendungskonfiguration = Anwendungskonfiguration(infrastruktur);

        anwendungskonfiguration.start();

        val api = anwendungskonfiguration.api()
        api.inventur.send(BeginneInventur(Aggregatkennung.neu()))
                .observable()
                .subscribe({a -> println(a)}, {e->println(e)})

        anwendungskonfiguration.stop()
    }
}

private fun <T> CompletableFuture<T>.observable(): Observable<T>
{
    return Observable.fromFuture(this)
}
