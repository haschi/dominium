package com.github.haschi.haushaltsbuch

import cucumber.api.java8.De

class HaushaltsbuchDienstSchrittdefinitionen(val dienst: HaushaltsbuchDienstRestSteps) : De
{
    init
    {
        Wenn("ich den Haushaltsbuchdienst starte"){}

        Und("ich die Version abfrage") {
            dienst.wennIchDieVersionAbfrage()
        }

        Dann("werde ich die Version \"([^\"]*)\" \"([^\"]*)\" erhalten") {
            service: String, version: String ->
                        dienst.prüfeVersion(service, version)
        }
    }
}
