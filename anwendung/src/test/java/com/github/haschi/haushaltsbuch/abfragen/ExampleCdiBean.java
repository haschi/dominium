package com.github.haschi.haushaltsbuch.abfragen;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by matthias on 16.07.16.
 */
public class ExampleCdiBean {

    @Inject
    EntityManager entityManager;

    public void testTransaction() {
        assertThat(this.entityManager.getTransaction().isActive()).isTrue();
    }
}
