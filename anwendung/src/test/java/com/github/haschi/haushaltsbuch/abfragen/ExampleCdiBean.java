package com.github.haschi.haushaltsbuch.abfragen;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleCdiBean
{

    @Inject
    EntityManager entityManager;

    public void testTransaction()
    {
        assertThat(this.entityManager.getTransaction().isActive()).isTrue();
    }
}
