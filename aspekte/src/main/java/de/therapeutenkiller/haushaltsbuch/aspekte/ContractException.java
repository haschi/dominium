package de.therapeutenkiller.haushaltsbuch.aspekte;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

/**
 * Created by mhaschka on 11.10.15.
 */
public class ContractException extends RuntimeException {

  private static final long serialVersionUID = 1997753363232807009L;

  public ContractException() {
  }

  public ContractException(String message) {
    super(message);
  }

  public ContractException(Signature signature) {
    super(signature.toLongString());
  }

  public ContractException(JoinPoint joinPoint) {
    joinPoint.getThis();
  }
  public ContractException(Throwable cause) {
    super(cause);
  }

  public ContractException(String message, Throwable cause) {
    super(message, cause);
  }

  public ContractException(
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
