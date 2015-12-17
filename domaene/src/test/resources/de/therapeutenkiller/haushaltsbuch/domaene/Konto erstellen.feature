# language: de
Funktionalität: Konto erstellen
  Als Hausmann möchte ich dem Haushaltsbuch Konten hinzufügen, um meine Ein- und Ausgaben zu gruppieren.

  Szenario: Konto ohne Anfangsbestand hinzufügen
    Angenommen ich habe mit der Haushaltsbuchführung begonnen
    Wenn wenn ich das Konto "Geldbörse" anlege
    #Event KontoWurdeAngelegt
    Dann wird das Konto "Geldbörse" für das Haushaltsbuch angelegt worden sein
    # Abfrage Saldo
    Und das Konto "Geldbörse" wird ein Saldo von 0,00 EUR besitzen