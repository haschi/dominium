# language: de
  Funktionalität: Anfangsbestand verbuchen
    Als Hausmann möchte ich den Anfangsbestand meiner Konten verbuchen
  damit ich eine Übersicht meines Vermögens erhalte

  Szenario: Konten mit Anfangsbeständen anlegen
    Angenommen ich habe mit der Haushaltsbuchführung begonnen
    Und ich habe das Konto "Girokonto" angelegt
    Wenn ich auf das Konto "Girokonto" den Anfangsbestand von 1234,56 EUR buche
    Dann wird das Konto "Girokonto" ein Sollsaldo von 1234,56 EUR haben
    Dann wird das Konto "Anfangsbestand" ein Habensaldo von 1234,56 EUR haben