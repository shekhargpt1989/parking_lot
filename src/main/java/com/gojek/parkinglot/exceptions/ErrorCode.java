package com.gojek.parkinglot.exceptions;

public enum ErrorCode {

  PARKING_LOT_ALREADY_EXISTS("Parking lot already exists."),
  PARKING_ALREADY_FULL("Sorry, parking lot is full"),
  INVALID_SLOT("Invalid slot id"),
  NO_SUCH_CARS_FOUND("Not found"),
  NO_PARKING_LOT_CREATED("No parking lot created"),

  INCORRECT_NUMBER_OF_PARAMS("Incorrect number of parameters passed"),
  INCORRECT_NUMBER_OF_SLOTS("Slots can only be number cannot be 0 or less"),
  INCORRECT_REGISTRATION("Incorrect registration number");



  final String message;

  ErrorCode(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
