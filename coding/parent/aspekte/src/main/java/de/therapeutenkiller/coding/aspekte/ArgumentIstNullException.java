package de.therapeutenkiller.coding.aspekte;

public class ArgumentIstNullException extends RuntimeException {

    private static final long serialVersionUID = 1997753363232807009L;

    public ArgumentIstNullException() {
        super();
    }

    public ArgumentIstNullException(final String message) {
        super(message);
    }
}
