package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.models.ValidationResult;

/**
 * Abstract validation Strategy
 */
public abstract class ValidationStrategy {

  private int numberOfParameters;


  public ValidationStrategy(int numberOfParameters) {
    this.numberOfParameters = numberOfParameters;
  }

  /**
   * Validates number of Request parameters.
   * @param parameters parameters.
   * @return result.
   */
  protected ValidationResult validateNumberOfParameters(String[] parameters) {
    if(parameters.length == numberOfParameters) {
     return new ValidationResult(true, null);
    } else {
      return new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_PARAMS);
    }
  }

  /**
   * Validates number of Request parameters.
   * @param parameters parameters
   * @return
   */
  public abstract ValidationResult validate(String[] parameters);
}
