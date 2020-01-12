package com.gojek.parkinglot.handler;

/**
 * Command
 */
public enum Command {

  CREATE("create"),
  PARK("park"),
  LEAVE("leave"),
  STATUS("status"),
  REG_OF_CARS_WITH_COLOUR("registration_numbers_for_cars_with_colour"),
  SLOT_OF_CARS_WITH_COLOUR("slot_numbers_for_cars_with_colour"),
  SLOT_OF_CAR_WITH_REG("slot_number_for_registration_number");


  final String name;

  Command(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
