package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.models.ValidationResult;

/**
 * Leave Request Validation.
 */
public class LeaveReqValidationStrategy extends ValidationStrategy {

  public LeaveReqValidationStrategy(int numberOfParameters) {
    super(numberOfParameters);
  }

  @Override
  public ValidationResult validate(String[] parameters) {
    return null;
  }
}
