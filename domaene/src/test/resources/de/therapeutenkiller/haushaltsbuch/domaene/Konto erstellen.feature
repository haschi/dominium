# language: de

Funktionalität: Konto erstellen
  Als Hausmann möchte ich dem Haushaltsbuch Konten hinzufügen, um die Geldbewegungen zu Gruppieren

  Szenario: Erstes Konto hinzufügen
    Angenommen ich habe mit der Haushaltsbuchführung begonnen
    Wenn ich dem Haushaltsbuch mein Konto "Geldbörse" mit einem Bestand von 123,00 EUR hinzufüge
    Dann wird mein ausgewiesenes Gesamtvermögen 123,00 EUR betragen
    Dann wird mein Gesamtvermögen auf 123,00 EUR erhöht worden sein.
    Dann wird meinem Haushaltsbuch das Konto "Geldbörse" hinzugefügt worden sein

  Szenariogrundriss: Konto hinzufügen
    Angenommen ich habe mit der Haushaltsbuchführung begonnen
    Und mein ausgewiesenes Gesamtvermögen beträgt <anfängliches Gesamtvermögen>
    Wenn ich ein Konto "<Konto>" mit einem Bestand von <Kontobestand> der Haushaltsbuchführung hinzufüge
    Dann wird mein ausgewiesenes Gesamtvermögen <abschließendes Gesamtvermögen> betragen
    Dann wird mein Gesamtvermögen auf <abschließendes Gesamtvermögen> geändert worden sein.

    Beispiele:
    | anfängliches Gesamtvermögen | Konto     | Kontobestand | abschließendes Gesamtvermögen |
    | 0,00 EUR                    | Geldbörse | 110,00 EUR   | 110,00 EUR                    |
    | 1235,89 EUR                 | Sparbuch  | 181,34 EUR   | 1417,23 EUR                   |
    | -304,44 EUR                 | Girokonto | 15678,23 EUR | 15373,79 EUR                  |
    | -1000,00 EUR                | Girokonto | 500,00 EUR   | -500,00 EUR                   |
