package com.github.haschi.dominium.persistenz.atom;

import com.github.haschi.dominium.modell.Domänenereignis;

public class EreignisNichtGespeichert extends RuntimeException {
    private final Domänenereignis<?> ereignis;

    public <T> EreignisNichtGespeichert(final Domänenereignis<T> ereignis) {
        super();
        this.ereignis = ereignis;
    }

    public <T> EreignisNichtGespeichert(final Domänenereignis<T> ereignis, final Throwable cause) {
        super(cause);
        this.ereignis = ereignis;
    }

    public <T> EreignisNichtGespeichert(final Domänenereignis<T> ereignis, final String message) {
        super(message);
        this.ereignis = ereignis;
    }
}
