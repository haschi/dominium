#language: de
  Funktionalität: Service identifizieren

    @system
    Szenario: Haushaltsbuchdienst starten
      Wenn ich den Haushaltsbuchdienst starte
      Und ich die Version abfrage
      Dann werde ich die Version "haushaltsbuch" "1.0" erhalten

    #@system
    Szenario: Haushaltsbuch ein zweites Mal starten
      Wenn ich den Haushaltsbuchdienst starte
      Und ich die Version abfrage
      Dann werde ich die Version "haushaltsbuch" "1.0" erhalten
