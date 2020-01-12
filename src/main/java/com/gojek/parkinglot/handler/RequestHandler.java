package com.gojek.parkinglot.handler;

import com.gojek.parkinglot.exceptions.ParkingLotException;
import com.gojek.parkinglot.models.SlotStatus;
import com.gojek.parkinglot.models.ValidationResult;
import com.gojek.parkinglot.services.ParkingLotService;
import com.gojek.parkinglot.validators.ValidationStrategy;
import com.gojek.parkinglot.validators.ValidatorRegistry;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RequestHandler {

  public static final String CREATE_RESPONSE = "Created a parking lot with %s slots";
  public static final String PARK_RESPONSE = "Allocated slot number: %s";
  public static final String LEAVE_RESPONSE = "Slot number %s is free";


  private final ValidatorRegistry validatorRegistry;
  private final ParkingLotService parkingLotService;

  /**
   * Constructor
   * @param validatorRegistry validation registry
   * @param parkingLotService parking lot service
   */
  public RequestHandler(ValidatorRegistry validatorRegistry,
    ParkingLotService parkingLotService) {
    this.validatorRegistry = validatorRegistry;
    this.parkingLotService = parkingLotService;
  }

  /**
   * execute request
   * @param request request
   * @return
   */
  public String execute(String request) {
    String[] params = request.split(" ");
    try {

      Command command = Command.of(params[0]);

      String[] requestParams = Arrays.copyOfRange(params, 1, params.length);

      ValidationStrategy validationStrategy = validatorRegistry
        .getValidationStrategy(command);

      ValidationResult validationResult = validationStrategy
        .validate(requestParams);

      if (!validationResult.isStatus())
        return validationResult.getErrorCode().getMessage();

      String response = null;
      try {
        switch (command) {
          case CREATE: {
            parkingLotService.createParkingLot(Integer.parseInt(requestParams[0]));
            response = String.format(CREATE_RESPONSE, requestParams[0]);
            break;
          }
          case PARK: {
            int slotId = parkingLotService.park(requestParams[1], requestParams[0]);
            response = String.format(PARK_RESPONSE, Integer.toString(slotId));
            break;
          }
          case LEAVE: {
            parkingLotService.leave(Integer.parseInt(requestParams[0]));
            response = String.format(LEAVE_RESPONSE, requestParams[0]);
            break;
          }
          case STATUS: {
            List<SlotStatus> result = parkingLotService.status();
            response = buildStatusResponse(result);
            break;
          }
          case REG_OF_CARS_WITH_COLOUR: {
            List<String> result = parkingLotService.registrationsByColour(requestParams[0]);
            StringBuilder sb = new StringBuilder();
            sb.append(result.get(0));
            IntStream.range(1, result.size()).forEach(i -> {
              sb.append(", ");
              sb.append(result.get(i));
            });
            response = sb.toString();
            break;
          }
          case SLOT_OF_CARS_WITH_COLOUR: {
            List<Integer> result = parkingLotService.slotIdsByColour(requestParams[0]);
            StringBuilder sb = new StringBuilder();
            sb.append(result.get(0));
            IntStream.range(1, result.size()).forEach(i -> {
              sb.append(", ");
              sb.append(result.get(i));
            });
            response = sb.toString();
            break;
          }
          case SLOT_OF_CAR_WITH_REG: {
            int slotId = parkingLotService.slotIdByRegistration(requestParams[0]);
            response = Integer.toString(slotId);
            break;
          }
        }
      } catch (ParkingLotException e) {
        response = e.getMessage();
      }
      return response;
    } catch (IllegalArgumentException e) {
      return "Command not found";
    }

  }

  private String buildStatusResponse(List<SlotStatus> result) {
    StringBuilder sb = new StringBuilder("Slot No.    Registration No    Colour");

    result.forEach(status -> {
      sb.append("\n");
      sb.append(status.getId());
      sb.append("           " + status.getRegistrationNumber());
      sb.append("      " + status.getColour());
    });

    return sb.toString();
  }

}
