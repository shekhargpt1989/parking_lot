package com.gojek.parkinglot.exceptions;

public enum ErrorCode {

  PARKING_LOT_ALREADY_EXISTS("Parking lot already exists."),
  PARKING_ALREADY_FULL("Parking already full"),
  INVALID_SLOT("Invalid slot id"),
  NO_SUCH_CARS_FOUND("No such cars found"),
  NO_PARKING_LOT_CREATED("No parking lot created");



  final String message;

  ErrorCode(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
