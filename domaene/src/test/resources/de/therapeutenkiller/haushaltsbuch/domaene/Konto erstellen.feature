# language: de
Funktionalität: Konto erstellen
  Als Hausmann möchte ich dem Haushaltsbuch Konten hinzufügen, um meine Ein- und Ausgaben zu gruppieren.

  Grundlage:
    Angenommen ich habe mit der Haushaltsbuchführung begonnen

  Szenario: Konto ohne Anfangsbestand hinzufügen
    Wenn wenn ich das Konto "Geldbörse" anlege
    Dann wird das Konto "Geldbörse" für das Haushaltsbuch angelegt worden sein
    Und das Konto "Geldbörse" wird ein Saldo von 0,00 EUR besitzen

  Szenario: Konto mit Anfangsbestand hinzufügen
    Wenn ich das Konto "Girokonto" mit einem Anfangsbestand von 1024,48 EUR anlege
    Dann wird das Konto "Girokonto" für das Haushaltsbuch angelegt worden sein
    Und das Konto "Girokonto" wird ein Saldo von 1024,48 EUR besitzen

  Szenario: Konto mehrfach hinzufügen
    Angenommen ich habe das Konto "Tagesgeld" angelegt
    Wenn wenn ich das Konto "Tagesgeld" anlege
    Dann wird das Konto "Tagesgeld" nicht angelegt