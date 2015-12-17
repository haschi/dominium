import de.therapeutenkiller.haushaltsbuch.domaene.ereignis.KontoWurdeAngelegt
import de.therapeutenkiller.haushaltsbuch.domaene.support.Wertobjekt
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

/**
 * Created by matthias on 17.12.15.
 */


public final class KontoWurdeAngelegtTest extends Specification {

    def "erfüllt die equals und hashCode Spezifikation"() {
        expect: EqualsVerifier
                .forClass(KontoWurdeAngelegt.class)
                .withRedefinedSuperclass()
                .verify();
    }
}