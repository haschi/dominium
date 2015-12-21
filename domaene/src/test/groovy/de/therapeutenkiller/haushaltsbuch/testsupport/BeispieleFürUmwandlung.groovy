package de.therapeutenkiller.haushaltsbuch.testsupport

import de.therapeutenkiller.coding.aspekte.ArgumentIstNullException

import javax.money.format.MonetaryParseException

/**
 * Created by matthias on 21.12.15.
 */
public class BeispieleFürUmwandlung {
    static def gültigeWährungsbeträge() {
        [
                ["123,45 EUR", 123.45, "EUR"],
                ["123.45 EUR", 12345.00, "EUR"],
                ["-10,23 USD", -10.23, "USD"],
                ["1  DEM", 1.00, "DEM"]
        ]
    }

    static def ungültigeWährungsbeträge() {
        [
                ["12.00 €", IllegalArgumentException.class],  // ungültig wegen € Zeichen
                ["Hello World", MonetaryParseException.class],    // Zeichenfolge ist ungültig
                ["", IllegalArgumentException.class],  // Leere Zeichfeolge ist ungültig
                [null, ArgumentIstNullException.class]  // Das ist auch Mist.

        ]
    }
}
