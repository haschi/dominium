# language: de
  Funktionalität: Anfangsbestand buchen
    Als Hausmann möchte ich den Anfangsbestand meiner Konten verbuchen
  damit ich eine Übersicht meines Vermögens erhalte

  Szenario: Konto mit einem Anfangsbestand anlegen
    Angenommen ich habe mit der Haushaltsbuchführung begonnen
    Und ich habe das Konto "Girokonto" angelegt
    Wenn ich auf das Konto "Girokonto" den Anfangsbestand von 1234,56 EUR buche
    Dann wird das Konto "Girokonto" ein Sollsaldo von 1234,56 EUR haben
    Dann wird das Konto "Anfangsbestand" ein Habensaldo von 1234,56 EUR haben

  Szenario: Konto mit mehreren Anfangsbeständen anlegen
    Angenommen ich habe mit der Haushaltsbuchführung begonnen
    Und ich habe das Konto "Sparbuch" angelegt
    Und ich habe auf das Konto "Sparbuch" den Anfangsbestand von 44444,55 EUR gebucht
    Wenn ich weitere 100,00 EUR auf das Konto "Sparbuch" als Anfangsbestand buche
    Dann werde ich die Buchung mit der Fehlermeldung "Der Anfangsbestand kann nur einmal für jedes Konto gebucht werden" abgelehnt haben