Testumgebung
============

Die Testpyramide ist in die folgendenen Ebenen aufgeteilt

1. Automatisierte Systemtests (vertikaler Schnitt, E2E)
2. API Integrationstests (ReST Schnittstelle des Backend) 
3. Integrationstests der Domäne

Je höher die Tests in der Pyramide liegen, umso teuerer ist deren
Ausführung.

Jeder Akzeptanztest kann auf einer oder mehreren Ebenen ausgeführt
werden. Dazu müssen die Szenarien mit den zur Ebene gehörenden Tags
versehen werden. Eine Mehrfachzuordnung ist möglich. Die Steps 
müssen in den Testautomationsmodulen der zugeordeneten Ebenen 
implementiert werden.

| Testebene                 | Tag für Cucumber Szenarien | Implementierungspackage | Runner       |
| 1. Systemtest             | @system                    | e2e                     | ./.          |
| 2. API Integration        | @api                       | rest                    | RunCukesIT   |
| 3. Integration der Domäne | @domäne                    | domaene                 | RunCukesTest |
