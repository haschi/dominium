package de.therapeutenkiller.haushaltsbuch.domaene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DomainEvents {

    private static final Map<Class<?>, List> LISTE =
        new ConcurrentHashMap<>();

    private DomainEvents() {
    }

    public static <T extends Domänenereignis> void registrieren(
        final Class<?> klasse,
        final Ereignishandler<T> handler) {

        if (!LISTE.containsKey(klasse)) {
            LISTE.put(klasse, new ArrayList<>());
        }

        // TODO: http://stackoverflow.com/questions/5309922/java-generics-how-to-avoid-unchecked-assignment-warning-when-using-class-hierar
        final List<Ereignishandler<?>> eineListe = LISTE.get(klasse);
        eineListe.add(handler); // NOPMD LoD
    }

    public static <T extends Domänenereignis> void auslösen(final T event) { // NOPMD UR

        if (LISTE.containsKey(event.getClass())) {
            for (final Object obj : LISTE.get(event.getClass())) { // NOPMD
                // TODO http://stackoverflow.com/questions/5309922/java-generics-how-to-avoid-unchecked-assignment-warning-when-using-class-hierar
                final Ereignishandler<T> handler = (Ereignishandler<T>) obj;
                handler.ausführen(event); //NOPMD LoD
            }
        }
    }

    public static void löschen() {
        LISTE.clear();
    }
}
