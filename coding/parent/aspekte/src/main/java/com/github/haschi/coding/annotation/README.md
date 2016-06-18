Regeln für Aggregatwurzeln
==========================

Aggregatwurzeln müssen einen public Modifizierer besitzen und dürfen nicht final sein.
Das Dominium Framework erzeugt eine Ableitung der Aggregatwurzel, um die Ereignisse
abzufangen. Das funktioniert nur, wenn die Aggregatwurzel auch ableitbar ist.

Aggregatwurzeln müssen ein Feld besitzen, das die Identität des Aggregats beschreibt.
Das Feld muss mit @AggregateIdentity annotiert werden und muss die Modifizierer
proteced final besitzen.

Die Aggregatwurzel führt die Geschäftslogik in Befehlshandlern durch. Die
Zuständsänderung dess Aggregats wird von der Geschäftslogik separiert und
 in einem eigenen Ereignishandler ausgeführt, der vom Befehlshandler
 aufgerufen werden muss.

 Ereignishandler sind Methoden, die mit @EventHandler annotiert sind.
 Die Methode muss den Modifizierer protected besitzen und darf keinen
 Wert zurückgeben (void). Ereignishandler müssen genau einen Parameter
 besitzen. Der Parameter ist das Ereignis, mit allen Informationen,
  die zur Zustandänderung benötigt werden.

