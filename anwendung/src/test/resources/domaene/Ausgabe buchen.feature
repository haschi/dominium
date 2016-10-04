# language: de
Funktionalität: Ausgabe buchen
  Als Hausmann möchte ich meine Ausgaben buchen, um eine Buchung auszuführen

  Grundlage:
    Angenommen mein Haushaltsbuch besitzt folgende Konten:
      | Kontoname    | Betrag       | Kontoart |
      | Girokonto    | 1200,00 EUR  | Aktiv    |
      | Lebensmittel | 345,00 EUR   | Aufwand  |
      | Gehalt       | 0,00 EUR     | Ertrag   |
      | Autokredit   | 13467,45 EUR | Passiv   |

  Szenario: Ausgabe auf vorhandene Konten buchen (Aufwand)
    Wenn ich meine Ausgabe von 56,78 EUR per "Lebensmittel" an "Girokonto" buche
    Dann werde ich folgende Kontostände erhalten:
      | Kontoname    | Betrag      | Kontoart |
      | Girokonto    | 1143,22 EUR | Aktiv    |
      | Lebensmittel | 401,78 EUR  | Aufwand  |
    Und ich werde den Buchungssatz "Lebensmittel (56,78 EUR) an Girokonto (56,78 EUR)" angelegt haben

  Szenariogrundriss: Ausgabe auf nicht vorhandenes Konto buchen
    Wenn ich meine Ausgabe von 56,78 EUR per "<Sollkonto>" an "<Habenkonto>" buche
    Dann werde ich die Buchung mit der Fehlermeldung "<Fehlermeldung>" abgelehnt haben

    Beispiele:
      | Sollkonto    | Habenkonto | Fehlermeldung                                        |
      | Tabakwaren   | Girokonto  | Das Konto Tabakwaren existiert nicht.                |
      | Lebensmittel | Sparbuch   | Das Konto Sparbuch existiert nicht.                  |
      | Tabakwaren   | Sparbuch   | Die Konten Tabakwaren und Sparbuch existieren nicht. |

  Szenario: Ausgabe auf Ertragskonto buchen
    Wenn ich meine Ausgabe von 56,78 EUR per "Gehalt" an "Girokonto" buche
    Dann werde ich die Buchung mit der Fehlermeldung "Ausgaben können nicht auf Ertragskonten gebucht werden." abgelehnt haben

  Szenario: Tilgung eines Verbraucherkredits (Verlust)
    Wenn ich meine Tilgung von 206,60 EUR per "Autokredit" an "Girokonto" buche
    Dann werde ich folgende Kontostände erhalten:
      | Kontoname  | Betrag       | Kontoart |
      | Girokonto  | 993,40 EUR   | Aktiv    |
      | Autokredit | 13260,85 EUR | Passiv   |
    Und ich werde den Buchungssatz "Autokredit (206,60 EUR) an Girokonto (206,60 EUR)" angelegt haben