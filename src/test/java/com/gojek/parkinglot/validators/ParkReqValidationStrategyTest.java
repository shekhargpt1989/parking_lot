package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.models.ValidationResult;
import org.junit.Assert;
import org.junit.Test;

/**'
 * ParkReqValidationStrategyTest.
 */
public class ParkReqValidationStrategyTest {

  private ValidationStrategy validationStrategy = new ParkReqValidationStrategy(2);

  /**
   * Correct params.
   */
  @Test
  public void validate_correctParameter_ShouldReturnValidationStatusTrue() {
    String[] input = {"KA-01-HH-1234","White"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertTrue(result.isStatus());
  }

  /**
   * In-Correct params.
   */
  @Test
  public void validate_incorrectRegistration_ShouldReturnValidationStatusFalse() {
    String[] input = {"KA-01-HH-12345-h","White"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertFalse(result.isStatus());
    Assert.assertEquals(ErrorCode.INCORRECT_REGISTRATION, result.getErrorCode());
  }

  /**
   * In-Correct number params.
   */
  @Test
  public void validate_incorrectNumberOfParameter_ShouldReturnValidationStatusFalse() {
    String[] input = {"KA-01-HH-12345-h","White", "3"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertFalse(result.isStatus());
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_SLOTS, result.getErrorCode());
  }

}
