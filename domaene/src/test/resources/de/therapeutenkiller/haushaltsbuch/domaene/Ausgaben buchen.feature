# language: de
    Funktionalität: Ausgaben buchen
      Als Hausmann möchte ich meine Ausgaben buchen, um eine kategorisierte Übersicht meiner Ausgaben zu erhalten

  Szenario: Ausgabe auf vorhandene Konten buchen
    Angenommen ich mein Haushaltsbuch besitzt folgende Konten:
    | Girokonto    | 1200,00 EUR | Haben |
    | Lebensmittel | 345,00 EUR  | Soll  |
    Wenn ich 56,78 EUR vom "Girokonto" an "Lebensmittel" buche
    Dann werde ich folgende Kontostände erhalten:
    | Girokonto    | 1143,22 EUR | Haben |
    | Lebensmittel | 401,78 EUR | Soll   |