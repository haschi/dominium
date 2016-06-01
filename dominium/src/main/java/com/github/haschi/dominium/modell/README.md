Architekturziel ist Domänenmodell möglichst frei von
technischen Belangen

Umsetzungsschritte:

Domänenmodelle werde annotiert, um das DDD Metamodell zu verwirklichen. Das bedeutet in der konkreten Umsetzung, dass
Aggregatwurzeln nicht von einer Basisklasse ableiten oder eine vorgegebene Schnittstelle implementeren müssen, sondern
mit einer Annotation versehen werden, um eine Klasse als Aggregatwurzel zu kennzeichnen. Ebenso werden Befehls- und
Ereignishandler durch Annotationen gekennzeichnet.

Durch die Verwendung von Annotationen wird die Abhängigkeit zum Dominium-Framework minimiert und das Domänenmodell frei
von Abhängigkeiten gehalten.

Das Framework implementiert die Infrastruktur mittels Annotation-Processing, Aspekten und Proxies.

Ereignisse entstehen innerhalb eines Aggregats. Sie sind das Äquivalent einer Zustandsänderung (y = f(x)). Dabei soll
f(x) das Ereignis und y die Zustandsänderung sein. Die Zustandsänderung y kann jederzeit aus dem
Ereignis gebildet werden, jedoch ist der umgekehrte Fall nur schwer möglich.

Während ein Aggregat die Zustandsänderung an sich selbst durchführen kann (und es ist die wichtigste Aufgabe der
Aggregatwurzel die Konsistenz des Aggregatzustands zu schützen), so ist es dem Aggregat nicht gegeben Ereignisse in die
Welt (und das Aggregat ist selbst ein Teil dieser Welt) zu publizieren, ohne dem Aggregat über die Existenz der Welt zu
informieren.

Lösungsmöglichkeiten:
    1. Es gibt eine Klasse mit einer statischen Methode, über die Ereignisse abgesendet werden.
    siehe: [http://udidahan.com/2009/06/14/domain-events-salvation/)]
    Dadurch wird jedoch Infrastruktur in das Aggregat gebracht. Ohne die Infrastruktur ist das Modell nicht
    verwendetbar.
    2. Die Kommandhandler des Aggregats geben Ereignisse als Rückgabewert zurück. Der Aufrufer ist dann
    verpflichtet das Ereignis anschließend auf das Aggregat anzuwenden. Das Aggregat benötigt kein Wissen über die Welt,
    in der es ausgeführt wird, kann jedoch nicht ohne die Infrastruktur in der sie eingebettet ist ausgeführt werden,
    da es nie zu einer Zustandsänderung kommt.
    3. Der Kommandhandler ruft den Eventhandler direkt auf. Dadurch kann das Aggregat sein Ereignis auf sich selbst
    Anwenden ohne von der Infrastruktur abhängig zu sein oder diese kennen zu müssen. Der Aufruf an den Ereignishandler
    wird von der Infrastruktur intercepted und das Ereignis abgefangen. Sofern die Zustandänderung des Aggregats
    erfolgreich durchgeführt wurde, kann das abgefangene Ereignis weiterverarbeitet werden;
    Also die Ereignisse persistiert und an die außerhalb des Aggregats liegenden Empfänger weitergeleitet werden.

Potentielle technische Varianten zu 3 sind:

    1. Der Application Service ruft das Aggregat über einen Proxy auf. Der Proxy überschreibt die Ereignishandler,
    fängt die Ereignisse ab, ruft den eigentlichen Ereignishandler des Aggregats auf und verarbeitet nach erfolgreicher
    Zustandsänderung selbst die Ereignisse bzw. leitet die Ereignisse zur weiteren Verarbeitung weiter.

    2. Mithilfe eines Aspektes werden die Aufrufe abgefangen und ähnlich wie in Lösung 1 weitergeleitet.

