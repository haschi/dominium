Ausführung der Unittests mit dem Spock Framework
================================================

Das Maven Surefire Plugin sucht selbstständig nach Plugins zur Ausführung von Unittests. Solange explizit oder implizit
ein durch transitive Abhängigkeiten Testframeworks wie JUnit oder TestNG im Klassenpfad liegen, verwendet Surefire
dieses automatisch. Wenn mehrere Testframeworks im Klassenpfad liegen, kann Surefire das Plugin nicht mehr automatisch
identifizieren.

Spock besitzt selbst eine Abhängigkeit zu JUnit, daher ist davon auszugehen, dass JUnit als Surefire Provider für die
Tests, die mit dem Spock Framework geschrieben sind, verwendet wird.

Sobald das Projekt direkt oder indirekt von TestNG abhägig ist, in dem testng oder cucumber-testng eingebunden werden,
kann das Surefire Plugin den Provider nicht mehr automatisch auflösen und verwendet den TestNG provider. Damit lassen
sich weder Cucumber Tests, da sie mit RunWith annotiert sind, noch Spock Test ausführen.


* http://maven.apache.org/surefire/maven-surefire-plugin/examples/providers.html
* http://spockframework.github.io/spock/docs/1.0/index.html

Hinweis zu den Aspekten
-----------------------

Um Probleme mit den Aspekten zu vermeiden, sollte bei Änderungen an diesen der Befehl
mvn clean install auf das aspekte Modul ausgeführt werden bevor die Tests in diesem Modul ausgeführt
 werden.

 Des weiteren werden aufgrund der dynamischen Struktur der Groovy Tests Compiler. Wenn die
 Testkonfiguration anstelle von Make  etwas ausführt wie,
 compile test-compile aspectj:compile aspectj:test-compile
 dann wird es besser.



