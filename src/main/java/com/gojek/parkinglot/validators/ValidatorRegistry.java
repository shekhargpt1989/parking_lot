package com.gojek.parkinglot.validators;

import com.gojek.parkinglot.handler.Command;
import java.util.HashMap;
import java.util.Map;

/**
 * Validators Registry
 */
public class ValidatorRegistry {

  private static ValidatorRegistry ourInstance = new ValidatorRegistry();

  public static ValidatorRegistry getInstance() {
    return ourInstance;
  }

  private final Map<Command,ValidationStrategy> commandMap;

  private ValidatorRegistry() {
    commandMap = new HashMap<>();
    commandMap.put(Command.CREATE, new CreateReqValidationStrategy(1));
    commandMap.put(Command.PARK, new ParkReqValidationStrategy(2));
    commandMap.put(Command.LEAVE, new LeaveReqValidationStrategy(1));
    commandMap.put(Command.STATUS, new StatusReqValidationStrategy(1));
    commandMap.put(Command.SLOT_OF_CAR_WITH_REG, new CarWithRegistrationReqValidationStrategy(1));
    commandMap.put(Command.SLOT_OF_CARS_WITH_COLOUR, new CarWithColourReqValidationStrategy(1));
    commandMap.put(Command.REG_OF_CARS_WITH_COLOUR, new CarWithColourReqValidationStrategy(1));
  }

  /**
   * provides Validation Strategy.
   * @param command command
   * @return Validation Strategy.
   */
  public ValidationStrategy getValidationStrategy(Command command) {
    return this.commandMap.get(command);
  }
}
