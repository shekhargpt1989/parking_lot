package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.models.ValidationResult;

/**
 * Park Request Validation.
 */
public class ParkReqValidationStrategy extends ValidationStrategy {

  public ParkReqValidationStrategy(int numberOfParameters) {
    super(numberOfParameters);
  }

  @Override
  public ValidationResult validate(String[] parameters) {
    return null;
  }
}
