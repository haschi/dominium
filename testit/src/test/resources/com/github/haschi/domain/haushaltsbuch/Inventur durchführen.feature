#language: de
Funktionalität: Inventur durchführen

  Als Hausmann möchte ich mein Guthaben und meine Schulden inventarisieren,
  um die Anfangsbestände meines Haushaltsbuches zu kennen

# http://www.it-infothek.de/wirtschaftsinformatik/semester-2/externes-rechnungswesen-03.html

  @domain
  Szenario: Inventur beginnen
    Wenn ich die Inventur beginne
    Dann wird mein Inventar leer sein

  @domain
  Szenario: Inventar erfassen
    Angenommen ich habe mit der Inventur begonnen
    Wenn ich folgendes Inventar erfasse:
      | Gruppe                | Position         | Währungsbetrag |
      | Anlagevermögen        | Sparbuchguthaben | 5.300,00 EUR   |
      | Umlaufvermögen        | Bankguthaben     | 500,00 EUR     |
      | Langfristige Schulden | Autokredit       | 10.569,00 EUR  |

    Dann werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben:
      | Position         | Währungsbetrag |
      | Sparbuchguthaben | 5.300,00 EUR   |

    Dann werde ich folgendes Umlaufvermögen in meinem Inventar gelistet haben:
      | Position     | Währungsbetrag |
      | Bankguthaben | 500,00 EUR     |

    Dann werde ich folgende Schulden in meinem Inventar gelistet haben:
      | Position   | Währungsbetrag |
      | Autokredit | 10.569,00 EUR  |

    Dann werde ich folgendes Reinvermögen ermittelt haben:
      | Summe des Vermögens         | 5.800,00 EUR  |
      | Summe der Schulden          | 10.569 EUR    |
      | Reinvermögen (Eigenkapital) | -4.769,00 EUR |

  @domain
  Szenario: Inventur ohne Inventarzählung beenden
    Angenommen ich habe mit der Inventur begonnen
    Wenn ich die Inventur beenden will
    Dann werde ich die Fehlermeldung "Kein Inventar erfasst" erhalten

  @domain
  Szenario: Inventar erfassen nach Beenden der Inventur
    Angenommen ich habe mit der Inventur begonnen
    Und ich habe folgendes Inventar erfasst:
      | Gruppe         | Position         | Währungsbetrag |
      | Anlagevermögen | Sparbuchguthaben | 5.300,00 EUR   |

    Wenn ich die Inventur beende

    Und ich folgendes Inventar erfassen will:
      | Gruppe         | Position     | Währungsbetrag |
      | Umlaufvermögen | Bankguthaben | 500,00 EUR     |

    Dann werde ich die Fehlermeldung "Inventur bereits beendet" erhalten

