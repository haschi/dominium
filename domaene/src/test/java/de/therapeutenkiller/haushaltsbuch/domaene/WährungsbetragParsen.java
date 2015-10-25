package de.therapeutenkiller.haushaltsbuch.domaene;

import javax.money.MonetaryAmount;

public interface WährungsbetragParsen {
    MonetaryAmount aus(String währungsbetrag);
}
