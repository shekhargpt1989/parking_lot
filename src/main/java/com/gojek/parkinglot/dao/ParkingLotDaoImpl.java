package com.gojek.parkinglot.dao;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.exceptions.ParkingLotException;
import com.gojek.parkinglot.models.Car;
import com.gojek.parkinglot.models.ParkingLot;
import com.gojek.parkinglot.models.Slot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParkingLotDaoImpl implements ParkingLotDao {

  protected ParkingLot parkingLot;

  @Override
  public void create(final ParkingLot parkingLot) {
    if (this.parkingLot != null) {
      throw new ParkingLotException(ErrorCode.PARKING_LOT_ALREADY_EXISTS);
    }
    this.parkingLot = parkingLot;
  }

  @Override
  public void updateSlot(final Slot slot) {
    checkIfParkingLotIsCreted();

    List<Slot> slots = parkingLot.getSlots();
    Slot matchingSlot = slots.stream().filter(slot1 -> slot1.getId() == slot.getId()).findFirst()
      .orElseThrow(() -> new ParkingLotException(ErrorCode.INVALID_SLOT));
    matchingSlot.setCar(slot.getCar());
  }

  @Override
  public List<Slot> getAllFilledSlots() {
    checkIfParkingLotIsCreted();

    List<Slot> slots = parkingLot.getSlots();
    List<Slot> filteredSlots = slots.stream().filter(slot1 -> slot1.getCar() != null)
      .collect(Collectors.toList());

    List<Slot> resultantList = cloneListOfSlots(filteredSlots);
    return resultantList.size() == 0 ? null : resultantList;

  }

  @Override
  public Slot getNearestAvailableSlot() {
    checkIfParkingLotIsCreted();

    List<Slot> slots = parkingLot.getSlots();
    Collections.sort(slots);
    Slot slot = slots.stream().filter(slot1 -> slot1.getCar() == null).findFirst().orElse(null);
    return cloneSlot(slot);
  }

  @Override
  public List<Slot> getSlotsByColour(final String colour) {
    checkIfParkingLotIsCreted();

    List<Slot> slots = parkingLot.getSlots();
    List<Slot> filteredSlots = slots.stream().filter(slot1 -> {
      if (slot1.getCar() == null) {
        return false;
      } else if (slot1.getCar().getColour().equals(colour)) {
        return true;
      } else {
        return false;
      }
    }).collect(Collectors.toList());

    List<Slot> resultantList = cloneListOfSlots(filteredSlots);
    return resultantList.size() == 0 ? null : resultantList;
  }

  @Override
  public Slot getSlotByRegistration(String registrationNumber) {
    checkIfParkingLotIsCreted();

    List<Slot> slots = parkingLot.getSlots();
    Slot slot = slots.stream().filter(slot1 -> {
      if (slot1.getCar() == null) {
        return false;
      } else if (slot1.getCar().getRegistrationNumber().equals(registrationNumber)) {
        return true;
      } else {
        return false;
      }
    }).findFirst().orElse(null);

    return cloneSlot(slot);
  }

  private void checkIfParkingLotIsCreted() {
    if (parkingLot == null) {
      throw new ParkingLotException(ErrorCode.NO_PARKING_LOT_CREATED);
    }
  }

  private List<Slot> cloneListOfSlots(final List<Slot> slots) {

    if(slots == null) return null;

    List<Slot> newList = new ArrayList<>();

    slots.forEach((slot) -> {
      Slot newSlot = cloneSlot(slot);
      newList.add(newSlot);
    });
    return newList;
  }

  private Slot cloneSlot(final Slot slot) {

    if(slot == null) return null;

    Slot newSlot = new Slot(slot.getId());
    Optional.ofNullable(slot.getCar()).ifPresent(car -> {
      Car newCar = new Car(car.getColour(), car.getRegistrationNumber());
      newSlot.setCar(newCar);
    });
    return newSlot;
  }
}
