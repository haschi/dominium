#language: de
Funktionalität: Inventur

  Als Hausmann möchte ich mein Guthaben und meine Schulden inventarisieren,
  um die Anfangsbestände meines Haushaltsbuches zu kennen

# http://www.it-infothek.de/wirtschaftsinformatik/semester-2/externes-rechnungswesen-03.html

  Szenario: Inventur beginnen
    Wenn ich die Inventur beginne
    Dann wird mein Inventar leer sein

  Szenario: Inventar erfassen
    Angenommen ich habe mit der Inventur begonnen
    Wenn ich folgendes Inventar erfasse:
      | Gruppe   | Untergruppe           | Position         | Währungsbetrag |
      | Vermögen | Anlagevermögen        | Sparbuchguthaben | 5.300,00 EUR   |
      | Vermögen | Umlaufvermögen        | Bankguthaben     | 500,00 EUR     |
      | Schulden | Langfristige Schulden | Autokredit       | 10.569,00 EUR  |

    Dann werde ich folgendes Anlagevermögen in meinem Inventar gelistet haben:
      | Position         | Währungsbetrag |
      | Sparbuchguthaben | 5.300,00 EUR   |

    Dann werde ich folgendes Umlaufvermögen in meinem Inventar gelistet haben:
      | Position     | Währungsbetrag |
      | Bankguthaben | 500,00 EUR     |

    Dann werde ich folgende Schulden in meinem Inventar gelistet haben:
      | Position   | Währungsbetrag |
      | Autokredit | 10.569,00 EUR  |

#    Dann werde ich folgendes Reinvermögen ermittelt haben:
#      | Summe des Vermögens         | 5.800,00 EUR  |
#      | Summe der Schulden          | 10.569 EUR    |
#      | Reinvermögen (Eigenkapital) | -4.769,00 EUR |
#
#    @ignore
#  Szenario: Inventur ohne Inventarzählung beenden
#    Angenommen ich habe mit der Inventur begonnen
#    Wenn ich die Inventur beenden will
#    Dann werde ich die Fehlermeldung "Kein Inventar erfasst" erhalten
#  @ignore
#  Szenario: Inventar erfassen nach Beenden der Inventur
#    Angenommen ich habe mit der Inventur begonnen
#    Und ich habe folgendes Inventar erfasst:
#      | Gruppe   | Untergruppe           | Position         | Währungsbetrag |
#      | Vermögen | Anlagevermögen        | Sparbuchguthaben | 5.300,00 EUR   |
#
#    Wenn ich die Inventur beende
#
#    Und ich folgendes Inventar erfassen will:
#      | Gruppe   | Untergruppe           | Position         | Währungsbetrag |
#      | Vermögen | Umlaufvermögen        | Bankguthaben     | 500,00 EUR     |
#
#    Dann werde ich die Fehlermeldung "Inventur bereits beendet" erhalten

