package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
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
    ValidationResult numberOfParamsResult = super.validateNumberOfParameters(parameters);

    if (!numberOfParamsResult.isStatus()) return numberOfParamsResult;

    try {
      int slotId = Integer.parseInt(parameters[0]);
      if(slotId < 1) return new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_SLOTS);
    } catch (NumberFormatException e) {
      return new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_SLOTS);
    }

    return new ValidationResult(true, null);
  }
}
