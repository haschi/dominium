# language: de

Funktionalität: Haushaltsbuchführung begonnen
  Als Hausmann
  möchte ich mit der Haushaltsbuchführung beginnen
  um eine Übersicht meines Vermögens zu erhalten

  Szenario: Haushaltsbuchführung beginnen
    Wenn ich mit der Haushaltsbuchführung beginne
    Dann wird mein ausgewiesenes Gesamtvermögen 0,00 EUR betragen

  Szenario: Erstes Konto hinzufügen
    Angenommen ich habe mit der Haushaltsbuchführung begonnen
    Wenn ich dem Haushaltsbuch mein Konto "Geldbörse" mit einem Bestand von 123,00 EUR hinzufüge
    Dann wird mein ausgewiesenes Gesamtvermögen 123,00 EUR betragen

  Szenariogrundriss: Konto hinzufügen
    Angenommen mein ausgewiesenes Gesamtvermögen beträgt <anfängliches Gesamtvermögen>
    Wenn ich ein Konto "<Konto>" mit einem Bestand von <Kontobestand> der Haushaltsbuchführung hinzufüge
    Dann wird mein ausgewiesenes Gesamtvermögen <abschließendes Gesamtvermögen> betragen

    Beispiele:
      | anfängliches Gesamtvermögen | Konto     | Kontobestand | abschließendes Gesamtvermögen |
      | 0,00 EUR                    | Geldbörse | 110,00 EUR   | 110,00 EUR                    |
      | 1235,89 EUR                 | Sparbuch  | 181,34 EUR   | 1417,23 EUR                   |
      | -304,44 EUR                 | Girokonto | 15678,23 EUR | 15373,79 EUR                  |
      | -1000,00 EUR                | Girokonto | 500,00 EUR   | -500,00 EUR                   |

  # Die folgenden Szenarien gehören nicht zur
  # Funktionalität Haushaltsbuchführung beginnen

  Szenario: Geld in Geldbörse stecken
    Angenommen ich habe eine leere Geldbörse
    Wenn ich 190,00 EUR in meine Geldbörse stecke
    Dann befinden sich 190,00 EUR in meiner Geldbörse

  Szenario: Geld zur Geldbörse hinzufügen
    Angenommen in meiner Geldbörse befinden sich 200,00 EUR
    Wenn ich 10,00 EUR in meine Geldbörse stecke
    Dann befinden sich 210,00 EUR in meiner Geldbörse




