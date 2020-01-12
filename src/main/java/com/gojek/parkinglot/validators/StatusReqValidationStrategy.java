package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.models.ValidationResult;

/**
 * Status Request Validation.
 */
public class StatusReqValidationStrategy extends ValidationStrategy {

  public StatusReqValidationStrategy(int numberOfParameters) {
    super(numberOfParameters);
  }

  @Override
  public ValidationResult validate(String[] parameters) {
    return null;
  }
}
