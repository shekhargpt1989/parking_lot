package com.gojek.parkinglot.dao;

import com.gojek.parkinglot.models.ParkingLot;
import com.gojek.parkinglot.models.Slot;
import java.util.List;

public class ParkingLotDaoInMemoryImpl implements ParkingLotDao {

  protected ParkingLot parkingLot;

  @Override
  public void create(ParkingLot parkingLot) {

  }

  @Override
  public void updateSlot(Slot slot) {

  }

  @Override
  public List<Slot> getAllFilledSlots() {
    return null;
  }

  @Override
  public Slot getNearestAvailableSlot() {
    return null;
  }

  @Override
  public List<Slot> getSlotsByColour(String colour) {
    return null;
  }

  @Override
  public Slot getSlotByRegistration(String registrationNumber) {
    return null;
  }
}
