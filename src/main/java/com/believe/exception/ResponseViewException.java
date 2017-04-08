package com.believe.exception;

import lombok.Getter;

import java.util.Map;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/5 23:34
 * @since 1.0
 */
@Getter
public class ResponseViewException extends RuntimeException {
  private final String view;
  private final Map<String, Object> result;

  public ResponseViewException(String view, Map<String, Object> result) {
    this.view = view;
    this.result = result;
  }

}
