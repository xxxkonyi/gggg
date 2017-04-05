package com.believe.exception;

public class XmlException extends RuntimeException {
  public XmlException() {
    super();
  }

  public XmlException(String message) {
    super(message);
  }

  public XmlException(String message, Throwable cause) {
    super(message, cause);
  }

  public XmlException(Throwable cause) {
    super(cause);
  }

  protected XmlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
