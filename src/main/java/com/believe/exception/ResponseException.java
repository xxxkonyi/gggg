package com.believe.exception;

import lombok.Getter;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:34
 * @since 1.0
 */
@Getter
public class ResponseException extends RuntimeException {
  private String messageCode;

  public ResponseException(String messageCode, Throwable cause) {
    super(cause);
    this.messageCode = messageCode;
  }

  public ResponseException(String messageCode) {
    this.messageCode = messageCode;
  }
}
