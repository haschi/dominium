# language: de
Funktionalität: Eröffnungsbilanz erstellen
  Als Hausmann
  möchte ich aus meinem Inventar eine Eröffnungsbilanz erstellen
  um fortlaufende Geschäftsvorfälle erfassen zu können

  @domäne
  Szenario: Eröffnungsbilanz erstellen
    Angenommen ich habe folgende Vermögenswerte in meinem Inventar erfasst:
      | Position  | Betrag      |
      | Sparbuch  | 2000,00 EUR |
      | Geldbörse | 350,00 EUR  |
      | Girokonto | 1150,00 EUR |
    Wenn ich die Eröffnungsbilanz aus dem Inventar erstelle
    Dann werde ich ein Eröffnungsbilanzkonto mit folgendem Inhalt erstellt haben:
      | Soll                               | Haben                             |
      | Anfangsbestand Bankkredit 3500 EUR | Anfangsbestand Sparbuch 2000 EUR  |
      |                                    | Anfangsbestand Geldbörse 350 EUR  |
      |                                    | Anfangsbestand Girokonto 1150 EUR |