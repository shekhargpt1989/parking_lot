package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.models.ValidationResult;

/**
 * Car with registration Request Validation.
 */
public class CarWithRegistrationReqValidationStrategy extends ValidationStrategy {

  public CarWithRegistrationReqValidationStrategy(int numberOfParameters) {
    super(numberOfParameters);
  }

  @Override
  public ValidationResult validate(String[] parameters) {
    return null;
  }
}
