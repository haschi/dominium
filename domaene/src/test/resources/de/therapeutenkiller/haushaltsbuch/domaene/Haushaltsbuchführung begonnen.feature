# language: de

Funktionalität: Haushaltsbuchführung begonnen
  Als Hausmann
  möchte ich mit der Haushaltsbuchführung beginnen
  um eine Übersicht meines Vermögens zu erhalten

  Szenario: Haushaltsbuchführung beginnen
    Wenn ich mit der Haushaltsbuchführung beginne
    Dann wird ein neues Haushaltsbuch mit einem Gesamtvermögen von 0,00 EUR angelegt worden sein

  Szenario: Haushaltsbuchführung nicht begonnen
    Angenommen ich habe noch nicht mit der Haushaltsbuchführung begonnen
    Dann wird kein Haushaltsbuch angelegt worden sein.


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




