package com.gojek.parkinglot.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Command
 */
public enum Command {

  CREATE("create_parking_lot"),
  PARK("park"),
  LEAVE("leave"),
  STATUS("status"),
  REG_OF_CARS_WITH_COLOUR("registration_numbers_for_cars_with_colour"),
  SLOT_OF_CARS_WITH_COLOUR("slot_numbers_for_cars_with_colour"),
  SLOT_OF_CAR_WITH_REG("slot_number_for_registration_number");

  private static final Map<String, Command> map = new HashMap<>(values().length, 1);

  static {
    for (Command command : values()) map.put(command.name, command);
  }

  final String name;

  Command(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static Command of(String name) {
    Command result = map.get(name);
    if (result == null) {
      throw new IllegalArgumentException("Invalid command name: " + name);
    }
    return result;
  }
}
