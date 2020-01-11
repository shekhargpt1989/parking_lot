package com.gojek.parkinglot.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Parking lot.
 */
public class ParkingLot {

  private final List<Slot> slots;
  private final int numberOfSlots;

  /**
   * Constructor.
   * @param numberOfSlots number of slots.
   */
  public ParkingLot(int numberOfSlots) {
    this.numberOfSlots = numberOfSlots;
    slots = IntStream.rangeClosed(1,numberOfSlots).mapToObj(i -> new Slot(i)).collect(Collectors.toList());
  }

  public List<Slot> getSlots() {
    return slots;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParkingLot that = (ParkingLot) o;
    return numberOfSlots == that.numberOfSlots &&
      equateList(slots, that.slots);
  }

  private boolean equateList(List<Slot> list1, List<Slot> list2) {
    if (list1.size() != list2.size()) return false;
    return (list1.stream().filter(slot -> !list2.contains(slot)).count()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(slots, numberOfSlots);
  }
}
