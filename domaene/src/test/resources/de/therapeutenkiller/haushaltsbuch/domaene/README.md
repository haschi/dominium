Anleitung für das Schreiben von Funktionalitäten und Szenarien
--------------------------------------------------------------

Die Funktionalitäten und deren Szenarien bilden einen wesentlichen Teil der
ubiquitären (allgegenwärtigen) Sprache (engl. ubiquitous language).

Für die sprachliche Abgrenzung zwischen Vorbedingungen, Anwendungsfällen und
Nachbedingungen in den Szenarien werden verschiedene Tempi benutzt, um eine
Überschneidung der Implementierungen im Glue Code zu vermeiden und präzise
die Intention auszudrücken.

Vorbedingung (Angenommen)
=========================

Die Vorbedingung wird im Perfekt beschrieben, um eine bereits abgeschlossene
Handlung zu beschreiben, die Auswirkung auf die Gegenwart besitzt. Die
Vorbedingung besteht aus ein oder mehreren abgeschlossenen Anwendungsfällen.

Beispiel:
```gherkin
Angenommen ich habe mit der Haushaltsbuchführung begonnen
Und ich habe das Konto "Girokonto" angelegt
```


Anwendungsfall (Wenn)
=====================

Ein Anwendungsfall beschreibt die Interaktionen zwischen Nutzer und System,
die notwendig sind, um ein fachliches Ziel des Nutzers zu verwirklichen.
Dabei dürfen die beschriebenen Abläufe nicht zu komplex werden.

Anwendungsfälle werden im Präsens verfasst.

Beispiel:
```gherkin
Wenn ich das Konto "Geldbörse" anlege
```

Nachbedingung (Dann)
====================

Die Nachbedingung stellt das Ergebnis eines vorangegangenen Anwendungsfalls
dar. Im vorliegenden System kann das Ergebnis entweder durch eine Abfrage
oder ein Domänenereignis geprüft werden.

Wenn die Ergebnisprüfung durch eine Abfrage erfolgt, dann wird die
Nachbedingung im Futur I verfasst.

Beispiel für Nachbedingung mit Prüfung durch Abfrage:
```gherkin
Dann wird das Konto "Girokonto" angelegt
```

Wenn die Nachbedingung durch ein abgefangenes Domänenereignis geprüft wird,
dann wird Futur II verwendet. Domänenereignis werden in der UL allgemein im
Perfekt beschrieben. Z.B. *Konto wurde angelegt* oder *Buchung wurde
ausgeführt*. Da aus Sicht des Anwendungsfalls die Nachbedingung erst in
der Zukunft erfüllt sein wird, bietet sich für die Formulierung der
Nachbedingung die im Futur II an, um die abgeschlossene Handlung auszudrücken.

Beispiel für Nachbedingung mit Prüfung durch Domänenereignis:
```gherkin
Dann wird das Konto "Girokonto" angelegt worden sein
```

Akteure und deren Personalpronomen
==================================

Ausgehend von der Standardformulierung von Funktionalitäten in der Form,
in der ein Akteur mit dem Personalpronomen *ich* gleichgesetzt wird:

```
Als Akteur möchte ich etwas tun, um einen Mehrwert zu erhalten
```

folgt, dass in den Szenarien das Personalpronomen der ersten Person singular
*ich* weiter als der Hauptakteur verwendet wird, für den die Funktionalität
vorgesehen ist.

Beispiel:
```gherkin
  Szenario: Haushaltsbuchführung beginnen
    Wenn *ich* mit der Haushaltsbuchführung beginne
    Dann werde *ich* ein neues Haushaltsbuch angelegt haben
    Und *ich* werde ein Gesamtvermögen von 0,00 EUR besitzen
```

Konfiguration von IntelliJ
--------------------------

Working directory: Wurzelverzeichnis des Moduls.
VM Options: -ea


![Konfiguration für Cucumber IntelliJ IDEA Plugin](Images/Konfiguration.png)
