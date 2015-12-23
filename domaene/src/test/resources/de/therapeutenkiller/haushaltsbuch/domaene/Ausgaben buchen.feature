# language: de
    Funktionalität: Ausgaben buchen
      Als Hausmann möchte ich meine Ausgaben buchen, um eine kategorisierte Übersicht meiner Ausgaben zu erhalten

  Szenario: Ausgabe auf vorhandene Konten buchen (Aktivtausch)
    Angenommen ich mein Haushaltsbuch besitzt folgende Konten:
    | Kontoname    | Betrag      | Kontoart |
    | Girokonto    | 1200,00 EUR | Aktiv    |
    | Lebensmittel | 345,00 EUR  | Aktiv    |
    Wenn ich 56,78 EUR vom "Girokonto" an "Lebensmittel" buche
    Dann werde ich folgende Kontostände erhalten:
    | Kontoname    | Betrag      | Kontoart |
    | Girokonto    | 1143,22 EUR | Aktiv    |
    | Lebensmittel | 401,78 EUR  | Aktiv    |