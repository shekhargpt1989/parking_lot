package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.models.ValidationResult;
import org.junit.Assert;
import org.junit.Test;

/**'
 * LeaveReqValidationStrategyTest.
 */
public class LeaveReqValidationStrategyTest {

  private ValidationStrategy validationStrategy = new LeaveReqValidationStrategy(1);

  /**
   * Correct params.
   */
  @Test
  public void validate_correctParameter_ShouldReturnValidationStatusTrue() {
    String[] input = {"6"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertTrue(result.isStatus());
  }

  /**
   * In-Correct params.
   */
  @Test
  public void validate_incorrectParameter_ShouldReturnValidationStatusFalse() {
    String[] input = {"-1"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertFalse(result.isStatus());
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_SLOTS, result.getErrorCode());
  }

  /**
   * In-Correct params.
   */
  @Test
  public void validate_NotANumberParameter_ShouldReturnValidationStatusFalse() {
    String[] input = {"hello"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertFalse(result.isStatus());
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_SLOTS, result.getErrorCode());
  }

  /**
   * In-Correct number params.
   */
  @Test
  public void validate_incorrectNumberOfParameter_ShouldReturnValidationStatusFalse() {
    String[] input = {"6", "20"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertFalse(result.isStatus());
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_SLOTS, result.getErrorCode());
  }

}
