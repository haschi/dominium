# language: de
Funktionalität: Einnahmen buchen
  Als Hausmann möchte ich meine Einnahmen buchen damit ich eine Übersicht meiner Einnahmen erhalten kann

  Grundlage:
    Angenommen mein Haushaltsbuch besitzt folgende Konten:
      | Kontoname | Betrag      | Kontoart |
      | Girokonto | 1200,00 EUR | Aktiv    |
      | Gehalt    | 0,00 EUR    | Ertrag   |

  Szenario: Einnahmen auf vorhandene Konten buchen
    Wenn ich meine Einnahmen von 3005,67 EUR per "Gehalt" an "Girokonto" buche
    Dann werde ich folgende Kontostände erhalten:
      | Kontoname | Betrag      | Kontoart |
      | Girokonto | 4205,67 EUR | Aktiv    |
      | Gehalt    | 3005,67 EUR | Ertrag   |

    