package de.therapeutenkiller.dominium.persistenz;

public class AggregatNichtGefunden extends Exception {

    private static final long serialVersionUID = -4782951573558519632L;

    public AggregatNichtGefunden(final Exception cause) {
        super(cause);
    }

    public AggregatNichtGefunden() {
        super();
    }
}
