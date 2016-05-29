package com.github.haschi.dominium.modell;

import com.github.haschi.dominium.testdomaene.ImmutableAnderesWertobjekt;
import com.github.haschi.dominium.testdomaene.ImmutableEinWertobjekt;
import com.mscharhag.oleaster.runner.OleasterRunner;
import com.github.haschi.dominium.testdomaene.EinWertobjekt;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.runner.RunWith;

import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(OleasterRunner.class)
public class WertobjektTest {

    private EinWertobjekt wertobjekt;

    {
        it("Wertobjekte entsprechen den Spezifikationen für equals() und hashCode()",() -> {
            EqualsVerifier.forClass(ImmutableEinWertobjekt.class)
                .suppress(Warning.NULL_FIELDS)
                .verify();
        });

        it("Wertobjekte können Felder für Äquivalenz ausschließen", () -> {
            EqualsVerifier.forClass(ImmutableAnderesWertobjekt.class)
                .withIgnoredFields("strasse")
                .suppress(Warning.NULL_FIELDS)
                .verify();
        });

        describe("Ein Wertobjekt", () -> {
            beforeEach(() -> {
                this.wertobjekt = ImmutableEinWertobjekt.builder()
                    .vorname("Matthias")
                    .nachname("Haschka")
                    .build();
            });

            it("liefert standardmäßig eine JSON ähnliche Repräsentation", () -> {
                assertThat(this.wertobjekt.toString())
                    .isEqualTo("EinWertobjekt{vorname=Matthias, nachname=Haschka}");
            });
        });
    }
}
