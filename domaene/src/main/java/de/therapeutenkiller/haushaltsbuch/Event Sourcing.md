Event Sourcing
==============

Bestandteile:

1. Kommando
2. Ereignis
3. Aggregat
4. Ereignis-Speicher (Event Store)
5. Anwendungsdienst (Application Service)


    Commands which request that something should happen, ie some state change
    Events which indicate that something has happened
    Aggregates that handles Commands and generates Events based on the current state
    Event store which stores all events that has happened
    Application services that receives Commands and routes it to the appropriate aggregate

Aufgaben des Application Service:

1. Events aus dem Event Store laden
2. Ein neues Aggregat instanziieren
3. Alle Ereignis auf das Aggregat anwenden
4. Das Kommando an das Aggregat senden
5. Die neuen Ereignisse speichern.

So funktioniert Event Sourcing
==============================


