package com.gojek.parkinglot.models;

import java.util.Objects;

/**
 * Slot
 */
public class Slot implements Comparable<Slot> {

  private final int id;
  private Car car;

  /**
   * Constructor for slot.
   * @param id id.
   */
  public Slot(int id) {
    this.id = id;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public int compareTo(Slot slot) {
    return Integer.compare(this.id, slot.id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Slot slot = (Slot) o;
    return id == slot.id &&
      Objects.equals(car, slot.car);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, car);
  }
}
