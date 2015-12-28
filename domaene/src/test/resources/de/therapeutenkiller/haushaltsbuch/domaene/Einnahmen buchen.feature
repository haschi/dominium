# language: de
Funktionalität: Einnahmen buchen
  Als Hausmann möchte ich meine Einnahmen buchen damit ich eine Übersicht meiner Einnahmen erhalten kann

  Grundlage:
    Angenommen mein Haushaltsbuch besitzt folgende Konten:
      | Kontoname | Betrag      | Kontoart |
      | Girokonto | 1200,00 EUR | Aktiv    |
      | Gehalt    | 0,00 EUR    | Ertrag   |

  Szenario: Einnahmen auf vorhandene Konten buchen
    Wenn ich meine Einnahme von 3005,67 EUR per "Girokonto" an "Gehalt" buche
    Dann werde ich folgende Kontostände erhalten:
      | Kontoname | Betrag      | Kontoart |
      | Girokonto | 4205,67 EUR | Aktiv    |
      | Gehalt    | 3005,67 EUR | Ertrag   |
    Und ich werde den Buchungssatz "Girokonto (3.005,67 EUR) an Gehalt (3.005,67 EUR)" angelegt haben


  Szenariogrundriss: Einnahme auf nicht vorhandenes Konto buchen
    Wenn ich meine Einnahme von 1357,24 EUR per "<Sollkonto>" an "<Habenkonto>" buche
    Dann werde ich die Buchung mit der Fehlermeldung "<Fehlermeldung>" abgelehnt haben

    Beispiele:
      | Sollkonto | Habenkonto | Fehlermeldung                                   |
      | Gehalt    | Sparbuch   | Das Konto Sparbuch existiert nicht.             |
      | Rente     | Girokonto  | Das Konto Rente existiert nicht.                |
      | Rente     | Sparbuch   | Die Konten Rente und Sparbuch existieren nicht. |