package de.therapeutenkiller.dominium.persistenz.jpa.testaggregat;

import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignislager;

import javax.persistence.EntityManager;

public class TestAggregatEreignislager extends JpaEreignislager<TestAggregatEreignis, TestAggregatEreignisziel> {

    public TestAggregatEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager, uhr);
    }
}
