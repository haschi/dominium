package com.github.haschi.haushaltsbuch.domaene.aggregat;

import javax.money.MonetaryAmount;

interface Buchungsregel
{
    boolean kannErtragBuchen(Buchungssatz buchungssatz);

    boolean kannVerlustBuchen(Buchungssatz buchungssatz);

    Buchungssatz buchungssatzFürAnfangsbestand(Konto konto, MonetaryAmount betrag);
}
