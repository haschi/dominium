package de.therapeutenkiller.dominium.modell;

/**
 * Ein Initialereignis tritt bei der Entstehung eines
 * Aggregates ein.
 *
 * Für jedes Aggregat muss mindestens ein Initialereignis
 * definiert sein. Initialereignisse werden im jeweiligen
 * Konstruktor des Aggregats ausgelöst.
 *
 * Die Implementierung des Initialereignisses muss sich das
 * Identitätsmerkmal des Aggregats merken.
 *
 * @param <A> Der Typ des Aggregats, zu dem das Initialereignis gehört.
 * @param <T> Der Typ des Identitätsmerkmals des Aggregats.
 */
public interface Initialereignis<A, T> extends Domänenereignis<A> {
    // BUG: Initialereignis kann mit anwendenAuf auf Aggregat angewendet werden. Darf es aber nicht
    T getIdentitätsmerkmal();
}