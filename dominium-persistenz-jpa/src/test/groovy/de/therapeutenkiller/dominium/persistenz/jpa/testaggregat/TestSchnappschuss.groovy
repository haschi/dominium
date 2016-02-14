package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat

import de.therapeutenkiller.dominium.modell.Schnappschuss
import de.therapeutenkiller.dominium.modell.Wertobjekt
import groovy.transform.Canonical
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ToString
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy

@ToString
@Builder(builderStrategy = InitializerStrategy)
class TestSchnappschuss extends Wertobjekt implements Schnappschuss<TestAggregat, Long>, Serializable {

    long zustand
    Long identitätsmerkmal
    long version;

    @Override
    TestAggregat wiederherstellen() {
        return new TestAggregat(this);
    }
}
