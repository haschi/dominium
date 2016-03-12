package de.therapeutenkiller.dominium.persistenz.atom;

public class EreignisstromNichtLesbar extends RuntimeException {
    public EreignisstromNichtLesbar(final Throwable grund) {
        super(grund);
    }
}
