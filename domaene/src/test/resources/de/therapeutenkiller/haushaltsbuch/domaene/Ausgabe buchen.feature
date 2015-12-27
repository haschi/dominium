# language: de
Funktionalität: Ausgabe buchen
  Als Hausmann möchte ich meine Ausgaben buchen, um eine kategorisierte Übersicht meiner Ausgaben zu erhalten

  Grundlage:
    Angenommen mein Haushaltsbuch besitzt folgende Konten:
      | Kontoname    | Betrag      | Kontoart |
      | Girokonto    | 1200,00 EUR | Aktiv    |
      | Lebensmittel | 345,00 EUR  | Aufwand  |

  Szenario: Ausgabe auf vorhandene Konten buchen (Aktivtausch)
    Wenn ich meine Ausgabe von 56,78 EUR per "Girokonto" an "Lebensmittel" buche
    Dann werde ich folgende Kontostände erhalten:
      | Kontoname    | Betrag      | Kontoart |
      | Girokonto    | 1143,22 EUR | Aktiv    |
      | Lebensmittel | 401,78 EUR  | Aufwand  |
    Und ich werde den Buchungssatz "Girokonto (56,78 EUR) an Lebensmittel (56,78 EUR)" angelegt haben

  Szenariogrundriss: Ausgabe auf nicht vorhandenes Konto buchen
    Wenn ich meine Ausgabe von 56,78 EUR per "<Sollkonto>" an "<Habenkonto>" buche
    Dann werde ich die Buchung mit der Fehlermeldung "<Fehlermeldung>" abgelehnt haben

    Beispiele:
    | Sollkonto | Habenkonto   | Fehlermeldung                                        |
    | Girokonto | Tabakwaren   | Das Konto Tabakwaren existiert nicht.                |
    | Sparbuch  | Lebensmittel | Das Konto Sparbuch existiert nicht.                  |
    | Sparbuch  | Tabakwaren   | Die Konten Sparbuch und Tabakwaren existieren nicht. |