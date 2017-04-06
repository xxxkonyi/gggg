package com.believe.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Lxp
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseUtils {

  public static <T> ResponseEntity<T> of(T body, HttpStatus statusCode) {
    return new ResponseEntity<T>(body, statusCode);
  }

  public static ResponseEntity of(HttpStatus statusCode) {
    return new ResponseEntity(statusCode);
  }

  public static ResponseEntity success() {
    return new ResponseEntity(HttpStatus.OK);
  }

  public static <T> ResponseEntity<T> success(T body) {
    return new ResponseEntity(body, HttpStatus.OK);
  }

  public static ResponseEntity notFound() {
    return ResponseEntity.notFound().build();
  }

}
