package com.gojek.parkinglot.models;

import java.util.Objects;

/**
 * Slot status
 */
public class SlotStatus {

  private final int id;
  private final String colour;
  private final String registrationNumber;

  public SlotStatus(int id, String colour, String registrationNumber) {
    this.id = id;
    this.colour = colour;
    this.registrationNumber = registrationNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SlotStatus that = (SlotStatus) o;
    return id == that.id &&
      colour.equals(that.colour) &&
      registrationNumber.equals(that.registrationNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, colour, registrationNumber);
  }
}
