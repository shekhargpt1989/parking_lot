package com.gojek.parkinglot.handler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.exceptions.ParkingLotException;
import com.gojek.parkinglot.models.SlotStatus;
import com.gojek.parkinglot.models.ValidationResult;
import com.gojek.parkinglot.services.ParkingLotService;
import com.gojek.parkinglot.validators.ValidationStrategy;
import com.gojek.parkinglot.validators.ValidatorRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Request handler tests.
 */
public class RequestHandlerTests {

  @Mock
  private ValidationStrategy validationStrategy;
  @Mock
  private ValidatorRegistry validatorRegistry;
  @Mock
  private ParkingLotService parkingLotService;
  private RequestHandler requestHandler;

  @Before
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(validatorRegistry.getValidationStrategy(Mockito.any())).thenReturn(validationStrategy);
    this.requestHandler = new RequestHandler(validatorRegistry, parkingLotService);
  }

  /**
   * Successful creation.
   */
  @Test
  public void execute_successfulCreate_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));

    String result = requestHandler.execute("create_parking_lot 6");
    Assert.assertEquals("Created a parking lot with 6 slots", result);
  }

  /**
   * UnSuccessful creation due to request validation.
   */
  @Test
  public void execute_unsuccessfulCreateDueToReqValidation_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_SLOTS));

    String result = requestHandler.execute("create_parking_lot 6");
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_SLOTS.getMessage(), result);
  }

  /**
   * UnSuccessful creation due to service exception.
   */
  @Test
  public void execute_unsuccessfulCreateDueToServiceEx_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    doThrow(new ParkingLotException(ErrorCode.PARKING_LOT_ALREADY_EXISTS)).when(parkingLotService).createParkingLot(6);

    String result = requestHandler.execute("create_parking_lot 6");
    Assert.assertEquals(ErrorCode.PARKING_LOT_ALREADY_EXISTS.getMessage(), result);
  }

  /**
   * Successful park.
   */
  @Test
  public void execute_successfulPark_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    when(parkingLotService.park(anyString(),anyString())).thenReturn(2);

    String result = requestHandler.execute("park KA-01-HH-1234 White");
    Assert.assertEquals("Allocated slot number: 2", result);
  }

  /**
   * UnSuccessful park due to request validation.
   */
  @Test
  public void execute_unsuccessfulParkDueToReqValidation_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(false, ErrorCode.INCORRECT_REGISTRATION));

    String result = requestHandler.execute("park KA-01-HH-12346 White");
    Assert.assertEquals(ErrorCode.INCORRECT_REGISTRATION.getMessage(), result);
  }

  /**
   * UnSuccessful park due to service exception.
   */
  @Test
  public void execute_unsuccessfulParkDueToServiceEx_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    doThrow(new ParkingLotException(ErrorCode.PARKING_ALREADY_FULL)).when(parkingLotService).park("White", "KA-01-HH-1234");

    String result = requestHandler.execute("park KA-01-HH-1234 White");
    Assert.assertEquals(ErrorCode.PARKING_ALREADY_FULL.getMessage(), result);
  }

  /**
   * Successful leave.
   */
  @Test
  public void execute_successfulLeave_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));

    String result = requestHandler.execute("leave 4");
    Assert.assertEquals("Slot number 4 is free", result);
  }

  /**
   * UnSuccessful leave due to request validation.
   */
  @Test
  public void execute_unsuccessfulLeaveDueToReqValidation_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(false, ErrorCode.INCORRECT_REGISTRATION));

    String result = requestHandler.execute("leave -1");
    Assert.assertEquals(ErrorCode.INCORRECT_REGISTRATION.getMessage(), result);
  }

  /**
   * UnSuccessful leave due to service exception.
   */
  @Test
  public void execute_unsuccessfulLeaveDueToServiceEx_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    doThrow(new ParkingLotException(ErrorCode.PARKING_ALREADY_FULL)).when(parkingLotService).leave(54);

    String result = requestHandler.execute("leave 54");
    Assert.assertEquals(ErrorCode.PARKING_ALREADY_FULL.getMessage(), result);
  }

  /**
   * Successful reg of cars with colours.
   */
  @Test
  public void execute_successfullyFindRegistrations_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    String[] registrations =  {"KA-01-HH-1234", "KA-01-HH-9999", "KA-01-P-333"};
    when(parkingLotService.registrationsByColour(anyString())).thenReturn(Arrays.asList(registrations));

    String result = requestHandler.execute("registration_numbers_for_cars_with_colour White");
    Assert.assertEquals("KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333", result);
  }

  /**
   * UnSuccessful reg of cars with colours due to request validation.
   */
  @Test
  public void execute_unsuccessfullyFindRegistrationsDueToReqValidation_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_PARAMS));

    String result = requestHandler.execute("registration_numbers_for_cars_with_colour White Red");
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_PARAMS.getMessage(), result);
  }

  /**
   * UnSuccessful reg of cars with colours due to service exception.
   */
  @Test
  public void execute_unsuccessfullyFindRegistrationsDueToServiceEx_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    doThrow(new ParkingLotException(ErrorCode.NO_SUCH_CARS_FOUND)).when(parkingLotService).registrationsByColour("White");

    String result = requestHandler.execute("registration_numbers_for_cars_with_colour White");
    Assert.assertEquals(ErrorCode.NO_SUCH_CARS_FOUND.getMessage(), result);
  }

  /**
   * Successful slots of cars with colours.
   */
  @Test
  public void execute_successfullyFindSlots_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    Integer[] slots =  {1, 2, 4};
    when(parkingLotService.slotIdsByColour(anyString())).thenReturn(Arrays.asList(slots));

    String result = requestHandler.execute("slot_numbers_for_cars_with_colour White");
    Assert.assertEquals("1, 2, 4", result);
  }

  /**
   * UnSuccessful slots of cars with colours due to request validation.
   */
  @Test
  public void execute_unsuccessfullyFindSlotsDueToReqValidation_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_PARAMS));

    String result = requestHandler.execute("slot_numbers_for_cars_with_colour White Red");
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_PARAMS.getMessage(), result);
  }

  /**
   * UnSuccessful slots of cars with colours due to service exception.
   */
  @Test
  public void execute_unsuccessfullyFindSlotsDueToServiceEx_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    doThrow(new ParkingLotException(ErrorCode.NO_SUCH_CARS_FOUND)).when(parkingLotService).slotIdsByColour("White");

    String result = requestHandler.execute("slot_numbers_for_cars_with_colour White");
    Assert.assertEquals(ErrorCode.NO_SUCH_CARS_FOUND.getMessage(), result);
  }

  /**
   * Successful slot of car with reg.
   */
  @Test
  public void execute_successfullyFindSlotWithReg_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    when(parkingLotService.slotIdByRegistration(anyString())).thenReturn(6);

    String result = requestHandler.execute("slot_number_for_registration_number KA-01-HH-3141");
    Assert.assertEquals("6", result);
  }

  /**
   * UnSuccessful slot of car with reg due to request validation.
   */
  @Test
  public void execute_unsuccessfullyFindSlotWithRegDueToReqValidation_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(false, ErrorCode.INCORRECT_REGISTRATION));

    String result = requestHandler.execute("slot_number_for_registration_number KA-01-HH-3141");
    Assert.assertEquals(ErrorCode.INCORRECT_REGISTRATION.getMessage(), result);
  }

  /**
   * UnSuccessful slot of car with reg due to service exception.
   */
  @Test
  public void execute_unsuccessfullyFindSlotWithRegDueToServiceEx_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    doThrow(new ParkingLotException(ErrorCode.NO_SUCH_CARS_FOUND)).when(parkingLotService).slotIdsByColour("White");

    String result = requestHandler.execute("slot_number_for_registration_number KA-01-HH-3141");
    Assert.assertEquals(ErrorCode.NO_SUCH_CARS_FOUND.getMessage(), result);
  }

  /**
   * Successful status with results.
   */
  @Test
  public void execute_successfulStatusWithResults_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    String expectedRes = "Slot No.\tRegistration No.\tColor\n1\tKA-01-HH-1234\tWhite";

    SlotStatus[] slotStatus = {new SlotStatus(1,"White", "KA-01-HH-123")};

    when(parkingLotService.status()).thenReturn(Arrays.asList(slotStatus));

    String result = requestHandler.execute("status");
    Assert.assertEquals(expectedRes, result);
  }

  /**
   * Successful status with empty results.
   */
  @Test
  public void execute_successfulStatusWithEmptyResults_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    String expectedRes = "Slot No.\tRegistration No.\tColor";

    when(parkingLotService.status()).thenReturn(new ArrayList<>());

    String result = requestHandler.execute("status");
    Assert.assertEquals(expectedRes, result);
  }

  /**
   * UnSuccessful status due to request validation.
   */
  @Test
  public void execute_unsuccessfulStatusDueToReqValidation_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(false, ErrorCode.INCORRECT_NUMBER_OF_PARAMS));

    String result = requestHandler.execute("status parkingLot");
    Assert.assertEquals(ErrorCode.INCORRECT_NUMBER_OF_PARAMS.getMessage(), result);
  }

  /**
   * UnSuccessful slot of car with reg due to service exception.
   */
  @Test
  public void execute_unsuccessfulStatusDueToServiceEx_returnExpectedResponse() {
    when(validationStrategy.validate(any())).thenReturn(new ValidationResult(true,null));
    doThrow(new ParkingLotException(ErrorCode.NO_PARKING_LOT_CREATED)).when(parkingLotService).status();

    String result = requestHandler.execute("status");
    Assert.assertEquals(ErrorCode.NO_PARKING_LOT_CREATED.getMessage(), result);
  }

}
