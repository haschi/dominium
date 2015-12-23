# language: de
Funktionalität: Ausgaben buchen
  Als Hausmann möchte ich meine Ausgaben buchen, um eine kategorisierte Übersicht meiner Ausgaben zu erhalten

  Grundlage:
    Angenommen ich mein Haushaltsbuch besitzt folgende Konten:
      | Kontoname    | Betrag      | Kontoart |
      | Girokonto    | 1200,00 EUR | Aktiv    |
      | Lebensmittel | 345,00 EUR  | Aktiv    |

  Szenario: Ausgabe auf vorhandene Konten buchen (Aktivtausch)
    Wenn ich 56,78 EUR vom "Girokonto" an "Lebensmittel" buche
    Dann werde ich folgende Kontostände erhalten:
      | Kontoname    | Betrag      | Kontoart |
      | Girokonto    | 1143,22 EUR | Aktiv    |
      | Lebensmittel | 401,78 EUR  | Aktiv    |

  Szenariogrundriss: Ausgabe auf nicht vorhandenes Konto buchen
    Wenn ich 56,78 EUR vom "<Sollkonto>" an "<Habenkonto>" buche
    Dann wird die Buchung mit der Fehlermeldung "<Fehlermeldung>" abgelehnt

    Beispiele:
    | Sollkonto | Habenkonto   | Fehlermeldung                                        |
    | Girokonto | Tabakwaren   | Das Konto Tabakwaren existiert nicht.                |
    | Sparbuch  | Lebensmittel | Das Konto Sparbuch existiert nicht.                  |
    | Sparbuch  | Tabakwaren   | Die Konten Sparbuch und Tabakwaren existieren nicht. |