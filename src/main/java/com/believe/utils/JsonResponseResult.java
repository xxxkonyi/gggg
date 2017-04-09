package com.believe.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/9 14:31
 * @since 1.0
 */
@Data
public class JsonResponseResult implements Serializable {

  public static final String OK = "ok";
  public static final String FAIL = "fail";

  private final String code;
  private Object body;

  private JsonResponseResult(String code) {
    this.code = code;
  }

  public static JsonResponseResult success() {
    return new JsonResponseResult(OK);
  }

  public static JsonResponseResult success(Object body) {
    JsonResponseResult responseResult = new JsonResponseResult(OK);
    responseResult.setBody(body);
    return responseResult;
  }

  public static JsonResponseResult fail() {
    return new JsonResponseResult(FAIL);
  }

  public static JsonResponseResult fail(Object body) {
    JsonResponseResult responseResult = new JsonResponseResult(FAIL);
    responseResult.setBody(body);
    return responseResult;
  }
}
