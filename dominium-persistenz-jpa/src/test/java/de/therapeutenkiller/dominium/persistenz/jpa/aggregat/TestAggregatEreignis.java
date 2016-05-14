package de.therapeutenkiller.dominium.persistenz.jpa.aggregat;

import de.therapeutenkiller.dominium.modell.Domänenereignis;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

public abstract class TestAggregatEreignis extends Domänenereignis<TestAggregatEreignisziel> {
}
