#language: de
Funktionalität: Private Eröffnungsbilanz erstellen

  Als Hausmann möchte ich eine private Eröffnungsbilanz erstellen
  damit ich mit der Haushaltsbuchführung beginnen kann

  # https://de.wikipedia.org/wiki/Privatbilanz
  Szenario: Private Eröffnungsbilanz erstellen
    Wenn ich die Inventur mit folgendem Inventar beende:
      | Gruppe         | Position | Betrag       |
      | Anlagevermögen | Sparbuch | 1.234,56 EUR |
      | Umlaufvermögen |          |              |
      | Schulden       |          |              |
      | Eigenkapital   |          | 1.234,56 EUR |

    Dann werde ich die folgende privaten Eröffnungsbilanz vorgeschlagen haben:
      | Seite  | Gruppe              | Posten   | Betrag       |
      | Aktiv  | A Anlagevermögen    | Sparbuch | 1.234,56 EUR |
      | Aktiv  | B Umlaufvermögen    |          |              |
      | Passiv | A Eigenkapital      |          | 1.234,56 EUR |
      | Passiv | C Verbindlichkeiten |          |              |
