package de.therapeutenkiller.dominium.persistenz.jpa.aggregat;

import de.therapeutenkiller.dominium.persistenz.jpa.JpaSchnappschuss;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

public abstract class TestAggregatSchnappschussBasis extends JpaSchnappschuss {

    private static final long serialVersionUID = -1219697897616903455L;
}
