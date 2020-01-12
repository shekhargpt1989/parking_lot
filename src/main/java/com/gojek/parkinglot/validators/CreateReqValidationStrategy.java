package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
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
    ValidationResult numberOfParamsResult = super.validateNumberOfParameters(parameters);

    if (!numberOfParamsResult.isStatus()) return numberOfParamsResult;

    try {
      int numOfSlots = Integer.parseInt(parameters[0]);
      if(numOfSlots < 1) return new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_SLOTS);
    } catch (NumberFormatException e) {
      return new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_SLOTS);
    }

    return new ValidationResult(true, null);
  }
}
