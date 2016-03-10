Regeln für die Programmierung
=============================

Das Projekt enthält vorgefertigte Konfigurationen und Hilfen für die Softwareentwicklung

Richtlinien
-----------

Verbindliche Konfiguration für die automatische Prüfung der Code-Richtlinien.

für Java

 * [Checkstyle](http://checkstyle.sourceforge.net/) 
 ([Regeln](http://checkstyle.sourceforge.net/checks.html)) 
 ([Maven Plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/))
 * [Findbugs](http://findbugs.sourceforge.net/) 
 ([Regeln](http://findbugs.sourceforge.net/bugDescriptions.html)) 
 ([Maven Plugin](http://gleclaire.github.io/findbugs-maven-plugin/))
 * [PMD](https://pmd.github.io/) 
 ([Regeln](http://pmd.sourceforge.net/pmd-5.3.2/pmd-java/rules/index.html)) 
 ([Maven Plugin](https://maven.apache.org/plugins/maven-pmd-plugin/))
 * Cobertura

für JavaScript/ECMAScript/TypeScript:

 * tslint

Aspekte
-------

Aspekte zum Einweben in andere Projekte. 

  * ["NullReferenzenAbschalten"]



Anwendung der Tiles
===================

Toolkonfigurationen werden als [Tiles](https://github.com/maoo/maven-tiles-examples) zu den
verwendeten Projekten hinzugefügt. Die folgenden Einstellungen müssen im POM der Projekte, im
Parent POM eines Multi-Module Projektes oder einem anderen geerbten Parent POM vorgenommen
werden:

```xml
<properties>
    <tile.coding-all>de.therapeutenkiller:coding-all:pom:0.0.1-SNAPSHOT</tile.coding-all>
    ...
</properties>

<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>io.repaint.maven</groupId>
                <artifactId>tiles-maven-plugin</artifactId>
                <version>2.1</version>
                <extensions>true</extensions>
                <configuration>
                    <tiles>
                        <tile>${tile.coding-all}</tile>
                    </tiles>
                </configuration>
            </plugin>
        </plugins>
    </pluginManagement>

    <plugins>
        <plugin>
            <groupId>io.repaint.maven</groupId>
            <artifactId>tiles-maven-plugin</artifactId>
            <extensions>true</extensions>
        </plugin>
    </plugins>
</build>
```
