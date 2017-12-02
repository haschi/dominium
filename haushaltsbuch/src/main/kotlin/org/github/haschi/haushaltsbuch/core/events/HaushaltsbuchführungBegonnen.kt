package org.github.haschi.haushaltsbuch.core.events

import org.github.haschi.haushaltsbuch.infrastruktur.modellierung.de.Aggregatkennung

data class HaushaltsbuchführungBegonnen(val id: Aggregatkennung)
