package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.models.ValidationResult;

/**
 * Create Request Validation.
 */
public class CreateReqValidationStrategy extends ValidationStrategy {

  public CreateReqValidationStrategy(int numberOfParameters) {
    super(numberOfParameters);
  }

  @Override
  public ValidationResult validate(String[] parameters) {
    return null;
  }
}
