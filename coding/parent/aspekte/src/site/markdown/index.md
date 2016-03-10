Aspekte
-------

Allgemeine Aspekte zum Einweben in Projekte.

Was geht nicht?
---------------
Prüfung der Parameter und Rückgabewerte von Lambda Ausdrücken ist nicht möglich, weil
die erforderlichen Joinpoints dafür nicht existieren.
Siehe [Java 8 lambda support (invokedynamic joinpoint)](https://bugs.eclipse.org/bugs/show_bug.cgi?id=471347)

Die Fehlermeldung der Ausnahme ```ArgumentIstNullException``` sollte den Namen des Parameters
enthalten, der null ist. Derzeit wird als Name 'argn' mit als nullbasierter Index des
Parameters zurückgegeben.
