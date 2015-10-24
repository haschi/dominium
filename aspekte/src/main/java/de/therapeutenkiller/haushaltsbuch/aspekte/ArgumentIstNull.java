package de.therapeutenkiller.haushaltsbuch.aspekte;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

public class ArgumentIstNull extends RuntimeException {

    private static final long serialVersionUID = 1997753363232807009L;

    public ArgumentIstNull() {
        super();
    }

    public ArgumentIstNull(final String message) {
        super(message);
    }

    public ArgumentIstNull(final Signature signature) {
        super(signature.toLongString());
    }

    public ArgumentIstNull(final JoinPoint joinPoint) {
        super();
        joinPoint.getThis();
    }

    public ArgumentIstNull(final Throwable cause) {
        super(cause);
    }

    public ArgumentIstNull(final String message, final Throwable cause) {
        super(message, cause);
    }
}
