package com.believe.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.util.*;

/**
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 */
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler({
    MissingServletRequestParameterException.class,
    UnsatisfiedServletRequestParameterException.class,
    HttpRequestMethodNotSupportedException.class,
    HttpMessageNotReadableException.class,
    MissingServletRequestPartException.class,
    ServletRequestBindingException.class,
    HttpMediaTypeNotSupportedException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public
  @ResponseBody
  ErrorMessage handleRequestException(Exception ex) {
    log.error("Http exception:" + ex);
    return new ErrorMessage(ex.getMessage(), ex.toString());
  }

  @ExceptionHandler(ResponseException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public
  @ResponseBody
  ErrorMessage handleResponseException(ResponseException ex) throws IOException {
    log.error("Http exception:" + ex);
    String message = messageSource.getMessage(ex.getMessageCode(), null, Locale.CHINESE);
    return new ErrorMessage(message, null);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public
  @ResponseBody
  Map<String, Object> handleUncaughtException(Exception ex) throws IOException {
    log.error("Http Status: " + HttpStatus.INTERNAL_SERVER_ERROR + " : " + ex.getMessage(), ex);
    Map<String, Object> map = new HashMap<>(2);
    map.put("error", "Unknown Error");
    if (ex.getCause() != null) {
      map.put("message", ex.getCause().getMessage());
    } else {
      map.put("message", ex.getMessage());
    }
    return map;
  }

}
