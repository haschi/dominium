package com.github.haschi.dominium.persistenz.jpa;

import com.github.haschi.dominium.modell.Schnappschuss;

import java.util.UUID;

public abstract class JpaSchnappschuss implements Schnappschuss<UUID> {

    private static final long serialVersionUID = -5757129473844941841L;

    public JpaSchnappschuss() {
        super();
    }
}
