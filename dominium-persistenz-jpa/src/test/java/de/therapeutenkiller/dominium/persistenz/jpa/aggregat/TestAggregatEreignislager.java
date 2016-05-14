package de.therapeutenkiller.dominium.persistenz.jpa.aggregat;

import de.therapeutenkiller.dominium.persistenz.Uhr;
import de.therapeutenkiller.dominium.persistenz.jpa.JpaEreignislager;

import javax.persistence.EntityManager;

public class TestAggregatEreignislager extends JpaEreignislager<TestAggregatEreignisziel> {

    public TestAggregatEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager);
    }
}
