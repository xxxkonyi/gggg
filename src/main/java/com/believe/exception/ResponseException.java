package com.believe.exception;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:34
 * @since 1.0
 */
public class ResponseException extends RuntimeException {
  private int code;
  private String message;

  public ResponseException(String message) {
    super(message);
    this.message = message;
  }
}
