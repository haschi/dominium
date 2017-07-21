# language: de
Funktionalität: Kontenrahmen anlegen
  Als Hausmann
  möchte einen Standardkontenplan verwenden
  um Buchungssätze zu kontieren

  @domäne
  Szenario: Standardkontenrahmen anlegen
    Wenn ich mit der Haushaltsbuchführung beginne
    Dann werde ich einen Kontenrahmen mit folgenden Konten für mein Hauptbuch angelegt haben:
      | Nummer | Bezeichnung | Art     |
      | 01     | Bankkonto   | Aktiv   |
      | 02     | Geldbörse   | Aktiv   |
      | 03     | Sparbuch    | Aktiv   |
      | 30     | Bankkredit  | Passiv  |
      | 10     | Gehalt      | Ertrag  |
      | 40     | Miete       | Aufwand |