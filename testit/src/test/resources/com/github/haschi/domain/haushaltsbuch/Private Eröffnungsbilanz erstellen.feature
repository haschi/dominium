#language: de
Funktionalität: Private Eröffnungsbilanz erstellen

  Als Hausmann möchte ich aus dem Inventar eine private Eröffnungsbilanz
  erstellen damit ich mit der Haushaltsbuchführung beginnen kann

  # https://de.wikipedia.org/wiki/Privatbilanz
  Szenario: Private Eröffnungsbilanz erstellen
    Wenn ich die Inventur mit folgendem Inventar beende:
      | Gruppe         | Kategorie | Position | Währungsbetrag |
      | Anlagevermögen | Sonstiges | Sparbuch | 1.234,56 EUR   |

    Dann werde ich die folgende private Eröffnungsbilanz vorgeschlagen haben:
      | Seite  | Gruppe           | Kategorie | Posten       | Betrag       |
      | Aktiv  | A Anlagevermögen | Sonstiges | Sparbuch     | 1.234,56 EUR |
      | Passiv | A Eigenkapital   |           | Eigenkapital | 1.234,56 EUR |

  Szenario: Private Eröffnungsbilanz mit Unterdeckung des Eigenkapitals
    Wenn ich die Inventur mit folgendem Inventar beende:
      | Gruppe         | Kategorie | Position   | Währungsbetrag |
      | Anlagevermögen | Sonstiges | Auto       | 5.000,00 EUR   |
      | Umlaufvermögen | Sonstiges | Girokonto  | 1.000,00 EUR   |
      | Schulden       | Sonstiges | Autokredit | 7.000,00 EUR   |

    Dann werde ich einen nicht durch Eigenkapital gedeckten Fehlbetrag in Höhe von "1.000,00 EUR" bilanziert haben