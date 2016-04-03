package de.therapeutenkiller.dominium.persistenz.jpa.aggregat;

import de.therapeutenkiller.dominium.persistenz.jpa.JpaDomänenereignis;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TestAggregatEreignis extends JpaDomänenereignis<TestAggregatEreignisziel> {
}
