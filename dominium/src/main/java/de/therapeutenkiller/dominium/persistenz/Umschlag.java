package de.therapeutenkiller.dominium.persistenz;

/*
 * Umschläge verbinden ein Objekt des Typs T mit Meta-Daten des
 * Typs M
 *
 * @param <T> Typs des zu kapselnden Objekts
 * @param <M> Meta-Daten Typ
 */
public interface Umschlag<T, M> {
    M getMetaDaten();

    T öffnen();
}
