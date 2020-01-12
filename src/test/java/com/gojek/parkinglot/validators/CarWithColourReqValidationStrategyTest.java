package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.models.ValidationResult;
import org.junit.Assert;
import org.junit.Test;

/**'
 * CarWithColourReqValidationStrategyTest.
 */
public class CarWithColourReqValidationStrategyTest {

  private ValidationStrategy validationStrategy = new CarWithColourReqValidationStrategy(1);

  /**
   * Correct params.
   */
  @Test
  public void validate_correctParameter_ShouldReturnValidationStatusTrue() {
    String[] input = {"White"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertTrue(result.isStatus());
  }


  /**
   * In-Correct number params.
   */
  @Test
  public void validate_incorrectNumberOfParameter_ShouldReturnValidationStatusFalse() {
    String[] input = {"White", "Red"};
    ValidationResult result = validationStrategy.validate(input);
    Assert.assertFalse(result.isStatus());
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_SLOTS, result.getErrorCode());
  }

}
