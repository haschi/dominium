# language: de
Funktionalität: Haushaltsbuchführung beginnen
  Als Hausmann
  möchte ich mit der Haushaltsbuchführung beginnen
  um ein Hauptbuch mit Konten und ein Journal anzulegen

  @api @fixture @domäne
  Szenario: Haushaltsbuchführung beginnen
    Wenn ich mit der Haushaltsbuchführung beginne
    Dann werde ich ein Hauptbuch mit Konten des Standard-Kontenrahmen angelegt haben
    Und ich werde ein Journal zum Hauptbuch angelegt haben