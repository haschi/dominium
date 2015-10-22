package de.therapeutenkiller.haushaltsbuch.aspekte;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

public class ContractException extends RuntimeException {

    private static final long serialVersionUID = 1997753363232807009L;

    public ContractException() {
    }

    public ContractException(final String message) {
        super(message);
    }

    public ContractException(final Signature signature) {
        super(signature.toLongString());
    }

    public ContractException(final JoinPoint joinPoint) {
        joinPoint.getThis();
    }

    public ContractException(final Throwable cause) {
        super(cause);
    }

    public ContractException(final String message, final Throwable cause) {
        super(message, cause);
    }

  /*
  @SuppressWarnings("checkstyle:parameternumber")
  public ContractException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
  */
}
