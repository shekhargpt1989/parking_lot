package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.models.ValidationResult;

/**
 * Car with colour Request Validation.
 */
public class CarWithColourReqValidationStrategy extends ValidationStrategy {

  public CarWithColourReqValidationStrategy(int numberOfParameters) {
    super(numberOfParameters);
  }

  @Override
  public ValidationResult validate(String[] parameters) {
    return null;
  }
}
