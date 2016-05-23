package com.github.haschi.dominium.persistenz.atom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.haschi.dominium.modell.Domänenereignis;

public class EreignisNichtSerialisierbar extends RuntimeException {

    private static final long serialVersionUID = 1901732937009343510L;

    private final Domänenereignis<?> ereignis;
    private final JsonProcessingException exception;

    public <T> EreignisNichtSerialisierbar(
            final Domänenereignis<T> ereignis,
            final JsonProcessingException exception) {
        super();
        this.ereignis = ereignis;
        this.exception = exception;
    }
}
