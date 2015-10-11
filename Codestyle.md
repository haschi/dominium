Installation der Google styleguide Einstellungen in IntelliJ und Eclipse
========================================================================

Installation in IntelliJ
------------------------

Kopiere die Datei intellij-java-google-style.xml in das config/codestyle Verzeichnis. Wähle unter
Settings/Code Style google-styleguide als aktuellen Code Style für das Projekt.


Installing the coding style settings in Intellij
----------------------------------------------

Download the intellij-java-google-style.xml file from the http://code.google.com/p/google-styleguide/ repo.
Copy it into your config/codestyles folder in your intellij settings folder.
 Under Settings/Code Style select the google-styleguide as current code style for the metanome project.

Installing the coding style settings in Eclipse
------------------------------------------------

Download the eclipse-java-google-style.xml file from the http://code.google.com/p/google-styleguide/ repo.
Under Window/Preferences select Java/Code Style/Formatter. Import the settings file by selecting Import.

POM configuration
-----------------

See [Multimodule Configuration](https://maven.apache.org/plugins/maven-checkstyle-plugin/examples/multi-module-config.html)
 at [Apache Maven Checkstyle Plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/)

 **Versuche nicht die Checkstyle Prüfung in die validate Phase einzubinden**. Das wird nicht
 funktionieren. Die Resourcen sind zu diesem Zeitpunkt noch nicht vorhanden.


