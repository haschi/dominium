Aspekte für SoSi20
==================

DisableBillionDollarBug 
-----------------------

Erlaubt keine null Parameter und Rückgabewerte. Wenn null als Parameter übergeben werden 
darf, dann muss der Parameter explizit mit @DarfNullSein annotiert werden. Methoden, die
null zurückgeben dürfen, müssen ebenfalls mit @DarfNullSein annotiert werden.

Wenn der Aspekt angewendet wird, dann wird eine ArgumentIstNullException bzw 
RückgabewertIstNullException ausgelöst, sollte eine Methode mit einem null Parameter
aufgerufen werden oder einen null Wert zurückgeben.

Was geht nicht?
---------------
Prüfung der Parameter und Rückgabewerte von Lambda Ausdrücken ist nicht möglich, weil
die erforderlichen Joinpoints dafür nicht existieren. 
Siehe [Java 8 lambda support (invokedynamic joinpoint)](https://bugs.eclipse.org/bugs/show_bug.cgi?id=471347)

Die Fehlermeldung der Ausnahme ArgumentIstNullException sollte den Namen des Parameters
enthalten, der null ist. Derzeit wird als Name 'argn' mit als nullbasierter Index des
Parameters zurückgegeben.

Anwendung des Aspekts in einem Projekt
--------------------------------------

Folgende Änderungen sind an der pom.xml des Projekts vorzunehmen, damit der Aspekt
angewendet werden kann:

```xml

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>aspectj-maven-plugin</artifactId>
                <version>1.8</version>
                <configuration>
                    <complianceLevel>1.8</complianceLevel>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                    <aspectLibraries>
                        <aspectLibrary>
                            <groupId>de.prosoz.sosi20.coding</groupId>
                            <artifactId>aspekte</artifactId>
                        </aspectLibrary>
                    </aspectLibraries>
                    <weaveDependencies>
                        <weaveDependency>
                            <groupId>de.prosoz.sosi20.coding</groupId>
                            <artifactId>aspekte</artifactId>
                        </weaveDependency>
                    </weaveDependencies>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>test-compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

TODO
----

Der Aspekt wird nicht auf Lambda Ausdrücke angewendet. Siehe
[Bug 471347 - Java 8 lambda support (invokedynamic joinpoint)](https://bugs.eclipse.org/bugs/show_bug.cgi?id=471347)
