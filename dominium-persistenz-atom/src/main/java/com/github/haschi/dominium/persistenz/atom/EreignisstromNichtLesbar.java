package com.github.haschi.dominium.persistenz.atom;

public class EreignisstromNichtLesbar extends RuntimeException {
    public EreignisstromNichtLesbar(final Throwable grund) {
        super(grund);
    }
}
