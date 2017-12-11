Dominium
========

Eine Buchführung für private Haushalte.


Anforderungen
-------------

Loslegen
--------

Frontend

Das Frontend kann mit dem Befehl npm run start oder der Run-Konfiguration start
ausgeführt werden, um manuelle Tests auszuführen. Das Frontend ist dann im 
Browser unter der URL http://localhost:4300 erreichbar

Unittests

Zur Ausführung von Unittests mit Karma dient die Run-Konfiguration Karma Test.

End to end Tests



Test
----

Die Tests benötigen Docker. Für die Installation auf Debian siehe
 https://docs.docker.com/engine/installation/linux/debian/#debian-jessie-80-64-bit
http://blog.arungupta.me/wildfly-admin-console-docker-image-techtip66/

Haushaltsbuch
=============

Eine Buchhaltung für den Privathaushalt.

1. [Anleitung für das Schreiben von Funktionalitäten und Szenarien](
domaene/src/test/resources/de/therapeutenkiller/haushaltsbuch/domaene/Anleitung.md)
2. [Funktionalitäten](domaene/src/test/resources/README.md)
2. [Buchführungsregeln](domaene/src/test/resources/de/therapeutenkiller/haushaltsbuch/domaene/Buchführungsregeln.md)
3. [Coding-Style - nicht aktuell](Codestyle.md)
4. [Build-Prozess](buildprozess.md)


Architektur
===========

Ziel ist die von [Jeffrey Palermo](http://jeffreypalermo.com/about/) in seinem Artikel 
[The Onion Architekture](http://jeffreypalermo.com/blog/the-onion-architecture-part-1/)
beschriebene Architektur zu verwendet.

Gründe:
* DDD und Onion Architecture verfolgen beide das Ziel den fachlichen Kern frei von technischen 
Belangen zu halten.
* Die Onion Architecture verfolgt ein durchgängiges, konsequentes und einfach zu verstehendes 
Konzept der Abhängigkeitsumkehrung (Dependency Inversion).
* Mit der traditionellen Tier-Architecture kann eine technische Durchdringung der Domäne nicht 
verhindert werden.

![Onion Architektur](http://jeffreypalermo.com/files/media/image/WindowsLiveWriter/TheOnionArchitecturepart1_70A9/image%7B0%7D%5B59%5D.png)

* Die Anwendung ist um ein unabhängiges Objektmodell herum entwickelt
* Innere Ebenen definieren Schnittstellen. Äußere Ebenen implementieren Schnittstellen
* Die Kopplung erfolgt von außen nach innen
* Der Code des Anwendungskerns kann unabhängig von der Infrastruktur übersetzt und ausgeführt werden.

Die Literatur enthält bislang keine Vorlagen, wie ein CQRS System respektive Axon Projekt auf die 
Onion Architektur konkret abgebildet werden kann. 

Domain Model
------------

Aggregate, Entitäten, Wertobjekte. Aber auch Commands, Events und Queries

Domain Services
---------------
Diensten, die Domänenkonzepte einbetten und bearbeiten und Teil der allgegenwärtigen Sprache sind.

Application Services
--------------------

Ein Anwendungsdienst hat eine wichtige und unterscheidende Rolle - er stellt eine Hosting-Umgebung 
für die Ausführung der Domänenlogik bereit. Somit ist es ein günstiger Punkt, um verschiedene 
Gateways wie ein Repository oder Wrapper für externe Dienste zu injizieren. Ein häufiges Problem 
beim Anwenden von DDD ist, wenn eine Entität Zugriff auf Daten in einem Repository oder einem 
anderen Gateway benötigt, um eine Geschäftsoperation auszuführen. Eine Lösung besteht darin, 
Repository-Abhängigkeiten direkt in die Entity zu injizieren, was jedoch oft verpönt ist. Ein 
Grund dafür ist, dass es erfordert, dass die Plain-Old- (C #, Java, usw.) Objekte, die Entitäten 
implementieren, Teil eines Anwendungsabhängigkeitsgraphen sind. Ein weiterer Grund ist, dass das 
Nachdenken über das Verhalten von Entitäten erschwert wird, da das Prinzip der einheitlichen 
Verantwortung verletzt wird. Eine bessere Lösung besteht darin, dass ein Anwendungsdienst die von 
einer Entität benötigten Informationen abruft, sodass die Ausführungsumgebung effektiv eingerichtet 
und der Entität bereitgestellt wird.

Oder auf deutsch:

Als Hosting-Umgebung kann die Konfiguration durch Axon betrachtet werden. Die Axon Konfiguration
stellt eben die Gateways zur Verfügung und bindet die von der Infrastruktur angebotenen konkreten
technischen Dienste ein: Event Store, Command-, Event- und Query-Bus ...

Die Infrastruktur muss also folgende Schnittstellen implementieren:

* Infrastrukturfabrik
    * Event Store
    * Command Bus
    * Logger
    * ...
 
Die Aufgabe besteht in der Registrierung der Command- und Eventhandler, um die korrekte Ausführung
der einzelnen Systembestandteile zu ermöglichen. 

Infrastructure
--------------
Infrastruktur Dienste konzentrieren um die technischen Aspekte der Anwendung. Dazu gehören 
Dateizugriffe, Datenbankzugriffe, E-Mail versandt und ähnliches.

Die Schnittstellen werden in der Domänenschicht deklariert und sind wichtige Aspekte der Domäne. 
Die Besonderheiten der technischen Umsetzung, wie zum Beispiel die Kommunikation mit dauerhaften 
Speichermechanismen werden jedoch in der Infrastrukturschicht behandelt.

Zielstruktur:
-------------
Mapping der Ringstruktur auf ein hierachische Modul und Package Struktur:

* **Infrastructure** (Maven Modul xxx-infrastructure)
    * **User Interface** (Package ui)
        * frontend (NPM Projekt mit Angular) ?
        * rest (Package)
    * **Test** (Package test)    
    * services (Package)
        * Infrastrukurfabrik (Implementierung)
* **Application Core** (Maven Modul xxx-core)
    * **Application Services** (Package application)
        * Spezialisierte Command Gateways
        * Query Gateway
        * Infrastrukturfabrik (Schnittstelle)
    * **Domain Services** (Package domain)
        * Sagas
        * Command Handler
        * Event Handler
        * Projektionen
    * **Domain Model** (Package model)
        * commands
        * events
        * queries
        * values
        * _Aggregate_
        * _Entitäten_
        * _Schnittstellen_

Zugriffsmatrix:

|................| Sagas | Command Handler | Event Handler | Projektionen| Commands | Events | Queries | Values |
|----------------|-------|-----------------|---------------|-------------|----------|--------|---------|--------|
| Sagas          |-      |                 |               |             | X        |X       | X       | X      |
| Command Handler|       |                 |               |             | X        |?       | X       | X      | 
| Event Handler  |       |                 |               |             | X        |X       | X       | X      |
| Projektionen   |       |                 |               |             |          |X       | X       | X      |
| Commands       |       |                 |               |             |          |        |         | X      |
| Events         |       |                 |               |             |          |        |         | X      |
| Queries        |       |                 |               |             |          |        |         | X      |
| Values         |       |                 |               |             |          |        |         | X      |

Vertikale Schnitte
==================

Falls die Domäne in Module aufgeteilt werden muss, ergibt sich dabei immer ein Shared Kernel, in
dem sich die gemeinsamen Teile der Modell aufhalten. 

* **Application Core**    
    * **Application Services**
    * **Shared Kernel** (Package shared)
        * **Domain Services**
        * **Domain Model**    
    * **Modul A**
        * **Domain Services**
        * **Domain Model**
    * **Modul B**
        * **Domain Service**
        * **Domain Model**


Getestet unter
--------------
* Wildfly 10.0.0.Final
* Maven 3.3.9
* Kotlin 1.2
* MariaDB Server Version 10.0.23, Client Version 1.3.7

