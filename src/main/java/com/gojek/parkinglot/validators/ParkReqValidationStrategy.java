package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.models.ValidationResult;
import com.gojek.parkinglot.utilities.RegistrationNumberUtil;

/**
 * Park Request Validation.
 */
public class ParkReqValidationStrategy extends ValidationStrategy {

  public ParkReqValidationStrategy(int numberOfParameters) {
    super(numberOfParameters);
  }

  @Override
  public ValidationResult validate(String[] parameters) {
    ValidationResult numberOfParamsResult = super.validateNumberOfParameters(parameters);

    if (!numberOfParamsResult.isStatus()) return numberOfParamsResult;

    if(!RegistrationNumberUtil.isValidRegistration(parameters[0])) return new ValidationResult(false, ErrorCode.INCORRECT_REGISTRATION);

    return new ValidationResult(true, null);
  }
}
