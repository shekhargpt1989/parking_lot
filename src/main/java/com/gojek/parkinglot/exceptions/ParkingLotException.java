package com.gojek.parkinglot.exceptions;

public class ParkingLotException extends RuntimeException {

  private final ErrorCode errorCode;

  public ParkingLotException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }


}
