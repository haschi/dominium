Hinweise zum Build-Prozess
==========================

Der Build-Prozess ist (wer hätte das gedacht) nicht trivial. Zur Realisierung werden Annotation Processing, Aspekte
Orientiert Programmierung (AOP) und Groovy verwendet. Dieses Dokument Listet die problematischen Werkzeuge auf und
erklärt die wichtigsten Fallstricke, die bei deren Verwendung zu beachten sind:

Compiler mit Annotation Processing
-----------------------------------

AspectJ
-------
Das Projekt verwendet nicht den AspectJ Compiler (ajc). Die Aspekte werden nachträglich eingewebt. Der
AspectJ Compiler kann das Annotation Processing nicht korrekt durchführen, da stets Velocity Version 1.5 für die
Codegenerierung durch die transitiven Abhängigkeiten verwendet wird. Das kann auch nicht abgeschaltet werden. Um die
Annotation Processing durch AspectJ auszuschalten muss `<proc>none</proc>` konfiguriert werden. Damit AspectJ nicht als
Compiler verwendet wird, müssen `<source/>` und `<testSource/>` jeweils leer sein. Allerdings muss dann
ein Verzeichnis angegeben werden, in dem die kompilierten Klassen zu finden sind. Das erfolgt mit
der `<weaveDirectory>${build.outputDirectory}</weaveDirectory>` Konfiguration.

Das Einweben der Aspekte erfolgt als jeweils letzter Schritt in den Phasen compile bzw. test-compile. Um das
sicherzustellen muss das aspectj-maven-plugin nach dem compiler- und gmavenplus-Plugin in der Maven POM deklariert
werden. Dazu muss mindestens maven in der Version 3.3 ausgeführt werden. Dies wird durch das Enforcer Plugin
sichergestellt.

Groovy
------

Groovy Compiler wird im zweiten Schritt in der Übersetzungsphase benutzt. Groovy wird für das Spock-Framework verwendet,
mit dem die Tests geschrieben werden.