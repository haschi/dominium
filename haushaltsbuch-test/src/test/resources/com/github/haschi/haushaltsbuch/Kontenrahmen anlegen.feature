# language: de
Funktionalität: Kontenrahmen anlegen
  Als Hausmann
  möchte einen Standardkontenplan verwenden
  um Buchungssätze zu kontieren

  @domäne
  Szenario: Standardkontenrahmen anlegen
    Wenn ich mit der Haushaltsbuchführung beginne
    Dann werde ich einen Kontenrahmen mit folgenden Konten für mein Hauptbuch angelegt haben:
      | Nummer | Bezeichnung           | Art     |
      | 01     | Sparbuch              | Aktiv   |
      | 21     | Geldbörse             | Aktiv   |
      | 22     | Bankkonto             | Aktiv   |
      | 41     | Bankkredit            | Passiv  |
      | 51     | Gehalt                | Ertrag  |
      | 61     | Miete                 | Aufwand |
      | 80     | Eröffnungsbilanzkonto | Bilanz  |