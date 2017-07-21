# language: de
Funktionalität: Konten anlegen
  Als Hausmann
  möchte einen Standardkontenplan verwenden
  um Buchungssätze zu kontieren

  @domäne
  Szenario: Standardkontenrahmen anlegen
    Wenn ich mit der Haushaltsbuchführung beginne
    Dann werden für das Hauptbuch meines Haushaltsbuches folgende Konten angelegt worden sein:
      | Nummer | Bezeichnung | Art     |
      | 01     | Bankkonto   | Aktiv   |
      | 02     | Geldbörse   | Aktiv   |
      | 03     | Sparbuch    | Aktiv   |
      | 30     | Bankkredit  | Passiv  |
      | 10     | Gehalt      | Ertrag  |
      | 40     | Miete       | Aufwand |