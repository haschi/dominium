package com.github.haschi.dominium.modell;

import com.github.haschi.dominium.testdomaene.generiert.TestAggregatProxy;
import com.mscharhag.oleaster.runner.OleasterRunner;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.runner.RunWith;

import java.util.UUID;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class EntitätTest {

    {
        final UUID identitätsmerkmal = UUID.randomUUID();

        describe("Eine Entität", () -> {

            it("benutzt das Identitätsmerkmal für den Äquivalenz-Vergleich", () ->
                EqualsVerifier.forClass(TestAggregatProxy.class)
                    .withOnlyTheseFields("id")
                    .verify());
        });
    }
}
