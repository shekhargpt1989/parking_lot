package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.models.ValidationResult;
import org.junit.Assert;
import org.junit.Test;

/**'
 * LeaveReqValidationStrategyTest.
 */
public class StatusReqValidationStrategyTest {

  private ValidationStrategy validationStrategy = new StatusReqValidationStrategy(0);

  /**
   * Correct params.
   */
  @Test
  public void validate_correctParameter_ShouldReturnValidationStatusTrue() {
    String[] input = {};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertTrue(result.isStatus());
  }


  /**
   * In-Correct number params.
   */
  @Test
  public void validate_incorrectNumberOfParameter_ShouldReturnValidationStatusFalse() {
    String[] input = {"6", "20"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertFalse(result.isStatus());
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_PARAMS, result.getErrorCode());
  }

}
