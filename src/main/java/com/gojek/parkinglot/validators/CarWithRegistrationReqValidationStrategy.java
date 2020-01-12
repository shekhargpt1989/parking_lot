package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.models.ValidationResult;
import com.gojek.parkinglot.utilities.RegistrationNumberUtil;

/**
 * Car with registration Request Validation.
 */
public class CarWithRegistrationReqValidationStrategy extends ValidationStrategy {

  public CarWithRegistrationReqValidationStrategy(int numberOfParameters) {
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
