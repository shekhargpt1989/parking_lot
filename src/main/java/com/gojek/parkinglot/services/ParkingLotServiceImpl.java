package com.gojek.parkinglot.services;

import com.gojek.parkinglot.dao.ParkingLotDao;
import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.exceptions.ParkingLotException;
import com.gojek.parkinglot.models.Car;
import com.gojek.parkinglot.models.ParkingLot;
import com.gojek.parkinglot.models.Slot;
import com.gojek.parkinglot.models.SlotStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParkingLotServiceImpl implements ParkingLotService {

  private ParkingLotDao parkingLotDao;

  public ParkingLotServiceImpl(ParkingLotDao parkingLotDao) {
    this.parkingLotDao = parkingLotDao;
  }

  @Override
  public void createParkingLot(int numberOfSlots) {
    final ParkingLot parkingLot = new ParkingLot(numberOfSlots);
    parkingLotDao.create(parkingLot);
  }

  @Override
  public int park(String colour, String registrationNumber) {

    Slot slot = Optional.ofNullable(parkingLotDao.getNearestAvailableSlot()).orElseThrow(() -> new ParkingLotException(
      ErrorCode.PARKING_ALREADY_FULL));

    Car car = new Car(colour, registrationNumber);
    slot.setCar(car);
    parkingLotDao.updateSlot(slot);
    return slot.getId();
  }

  @Override
  public void leave(int slotId) {
    Slot slot = new Slot(slotId);
    parkingLotDao.updateSlot(slot);
  }

  @Override
  public List<String> registrationsByColour(String colour) {
    List<Slot> slots = Optional.ofNullable(parkingLotDao.getSlotsByColour(colour)).orElseThrow(() -> new ParkingLotException(ErrorCode.NO_SUCH_CARS_FOUND));
    return slots
      .stream()
      .map(slot -> slot.getCar().getRegistrationNumber())
      .collect(Collectors.toList());
  }

  @Override
  public List<Integer> slotIdsByColour(String colour) {
    List<Slot> slots = Optional.ofNullable(parkingLotDao.getSlotsByColour(colour)).orElseThrow(() -> new ParkingLotException(ErrorCode.NO_SUCH_CARS_FOUND));
    return slots
      .stream()
      .map(slot -> slot.getId())
      .collect(Collectors.toList());
  }

  @Override
  public int slotIdByRegistration(String registrationNumber) {
    Slot slot = Optional.ofNullable(parkingLotDao.getSlotByRegistration(registrationNumber)).
      orElseThrow(() -> new ParkingLotException(ErrorCode.NO_SUCH_CARS_FOUND));
    return slot.getId();
  }

  @Override
  public List<SlotStatus> status() {
    List<Slot> slots = Optional.ofNullable(parkingLotDao.getAllFilledSlots()).orElse(new ArrayList<>());
    return slots
      .stream()
      .map(slot -> new SlotStatus(slot.getId(), slot.getCar().getColour(), slot.getCar().getRegistrationNumber()))
      .collect(Collectors.toList());
  }
}
