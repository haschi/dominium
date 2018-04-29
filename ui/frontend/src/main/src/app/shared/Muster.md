# Umsetzung des Redux Muster

Redux ist ein rein funktionales Muster. Alle Elemente des Musters werden in 
einer Datei, die mit .redux.ts endet zusammengefasst. Das Zusammenspiel von
Reducer, Action bzw. Action Creator, Modell und Epics entspricht einer
Klasse:
* Zustand: Felder -> Store
* Zustandsänderung: 
    * Methoden -> Action, 
    * Methodensignatur -> Action Creator
    * Zuweisung -> Reducer
* Seiteneffekte -> Epic (bedingt)

Der Präfix des Dateinamens ist die Entität, welche durch das Muster beschrieben 
wird.

Beispiele:

* inventur.redux.ts
* haushaltsbuch.redux.ts
* command.redux.ts
* query.redux.ts 

Die Struktur einer *.redux.ts Datei sieht folgendermaßen aus:

* Konstanten
* Model
* Action Creator
* Reducer
* Epics
    
## Konstanten

Konstanten werden für die Typisierung von Actions verwendet. Jede Action ist
durch ihren Typ identifizierbar. Reducer verwenden den Typ, um die jeweilige 
Aktion zu erkennen und einen für die Aktion spezifische Transformation des 
Zustandes auszuwählen

Namenskonvention: [[TODO]]

**Konstanten werden nicht exportiert**, damit Actions nur durch Action Creator 
erzeugt werden.

## Model

Das Model besteht i.d.R. aus Interfaces, welche die Struktur des States
beschrieben.

Namenskonvention: Für die Beschreibung der Entität(en) werden i.d.R. Substantive
verwendet.

## Action Creator

Action Creator sind reine Funktionen, die Actions erzeugen. Sie führen keine
Seiteneffekte durch.

Die Benennung von Action Creator folgt dem Schema *substantivVerb*. Das 
Substantiv ist oft der Name der Entität, welche durch das Muster beschrieben 
wird. Das Verb beschreibt eine Aktion, welche auf die Entität angewendet wird.    

Action Creator werden exportiert.

## Reducer

Das Muster besitzt eine Funktion zur Änderung des Zustandes einer Entität
anhand einer Action.

Reducer sind reine Funktionen mit der Signatur reducer: (state, action) -> state.
Reducer führen keine Seiteneffekte aus. Insbesondere ändern sie nicht den Wert
der ihnen übergebenen Parameter. Der zurückgegebene State ist immer ein neuer
State.

Namenskonvention: *entität*. (Die Funktion besitzt den Namen der Entität, klein
geschrieben. Dies ist die allgemeine Konvention des Redux Musters)

## Epics

Epics sind automatische Prozess-Schritte, die von einer Aktion ausgelöst werden.
Diese Prozess-Schritte liefern weitere Aktionen, welche zusätzliche 
Zustandsänderungen bewirken. Seiteneffekte sind oft deren Ursache und werden
von Epics ausgelöst.

Sofern Seiteneffekte werden von Diensten implementiert und bereitgestellt. 
Instanzen dieser Dienste werden dem Epic als Parameter übergeben.

Epics sind Funktionen höherer Ordnung (higher order function). Sie erhalten
die Instanzen zustandsfreier Dienste als Parameter und liefern eine Funktion
die zu einer gegebenen Aktion den Seiteneffekt ausführt und als Ergebnis
weitere Aktionen liefert:

```typescript
function fallsQueryAngefordert(service: QueryGatewayService): Epic<QueryAction, AppState> {
    return (action$) => action$
        .ofType(QueryGatewayActionType.angefordert)
        .mergeMap(action => service.get(action as QueryMessageAction)
            .map(response => queryGelungen(action.message, response))
            .catch(error => of(queryFehlgeschlagen(action.message, error.status, "Fehler"))));
}
```   

Namenskonvention: *fallsAktion*, wobei *Aktion* die Bezeichnung eines Action Creator ist,
der eine Aktion liefert, der die Ursache einer Zustandsänderung ist. Beispiel:

* fallsQueryAngefordert
* fallsQueryErfolgreich
* fallsQueryFehlgeschlagen
* fallsCommandAngefordert
* ...
