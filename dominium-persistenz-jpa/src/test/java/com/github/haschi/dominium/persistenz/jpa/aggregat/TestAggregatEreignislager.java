package com.github.haschi.dominium.persistenz.jpa.aggregat;

import com.github.haschi.dominium.persistenz.Uhr;
import com.github.haschi.dominium.persistenz.jpa.JpaEreignislager;

import javax.persistence.EntityManager;

public class TestAggregatEreignislager extends JpaEreignislager<TestAggregatEreignisziel> {

    public TestAggregatEreignislager(final EntityManager entityManager, final Uhr uhr) {
        super(entityManager);
    }
}
