package de.therapeutenkiller.haushaltsbuch.persistenz;

import de.therapeutenkiller.dominium.jpa.HibernateEventStore;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.Haushaltsbuch;
import de.therapeutenkiller.haushaltsbuch.domaene.aggregat.HaushaltsbuchSchnappschuss;

public class HaushaltsbuchEventStore extends HibernateEventStore<HaushaltsbuchSchnappschuss, Haushaltsbuch> {
}
