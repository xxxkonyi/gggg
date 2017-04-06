package com.believe.exception;

import lombok.Value;

import java.util.Date;

@Value
public class ErrorMessage {
  private final String message;
  private final String exception;
}
