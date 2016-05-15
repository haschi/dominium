package de.therapeutenkiller.dominium.modell;

import com.mscharhag.oleaster.runner.OleasterRunner;
import de.therapeutenkiller.coding.aspekte.ValueObject;
import de.therapeutenkiller.dominium.testdomäne.AnderesWertobjekt;
import de.therapeutenkiller.dominium.testdomäne.EinWertobjekt;
import nl.jqno.equalsverifier.EqualsVerifier;
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
            EqualsVerifier.forClass(EinWertobjekt.class).verify();
        });

        it("Wertobjekte werden durch @ValueObject Annotation deklariert", () -> {
            assertThat(EinWertobjekt.class.getAnnotation(ValueObject.class))
                .isNotNull();
        });

        it("Wertobjekte können Felder für Äquivalenz ausschließen", () -> {
            EqualsVerifier.forClass(AnderesWertobjekt.class)
                .withIgnoredFields("straße")
                .withRedefinedSuperclass()
                .verify();
        });

        describe("Ein Wertobjekt", () -> {
            beforeEach(() -> {
                this.wertobjekt = new EinWertobjekt("Matthias", "Haschka");
            });

            it("liefert standardmäßig eine JSON ähnliche Repräsentation", () -> {
                assertThat(this.wertobjekt.toString())
                    .isEqualTo("{\"vorname\":\"Matthias\",\"nachname\":\"Haschka\"}");
            });
        });
    }
}
