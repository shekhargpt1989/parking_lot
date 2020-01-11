package com.gojek.parkinglot.services;

import com.gojek.parkinglot.models.SlotStatus;
import java.util.List;

public class ParkingLotServiceImpl implements ParkingLotService {

  @Override
  public void createParkingLot(int numberOfSlots) {

  }

  @Override
  public int park(String colour, String registrationNumber) {
    return 0;
  }

  @Override
  public void leave(int slotId) {

  }

  @Override
  public List<String> registrationsByColour(String colour) {
    return null;
  }

  @Override
  public List<Integer> slotIdsByColour(String colour) {
    return null;
  }

  @Override
  public int slotIdByRegistration(String registrationNumber) {
    return 0;
  }

  @Override
  public List<SlotStatus> status() {
    return null;
  }
}
