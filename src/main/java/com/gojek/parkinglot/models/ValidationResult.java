package com.gojek.parkinglot.models;

import com.gojek.parkinglot.exceptions.ErrorCode;

/**
 * Validation Result
 */
public class ValidationResult {
    private final boolean status;
    private final ErrorCode errorCode;

  /**
   * Constructor
   * @param status status.
   * @param errorCode error code.
   */
  public ValidationResult(boolean status, ErrorCode errorCode) {
    this.status = status;
    this.errorCode = errorCode;
  }

  public boolean isStatus() {
    return status;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
