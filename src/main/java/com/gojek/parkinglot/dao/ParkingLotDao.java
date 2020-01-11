package com.gojek.parkinglot.dao;

import com.gojek.parkinglot.models.Car;
import com.gojek.parkinglot.models.ParkingLot;
import com.gojek.parkinglot.models.Slot;
import java.util.List;

public interface ParkingLotDao {

  /**
   * Create Parking lot.
   * @param parkingLot parking lot.
   */
  public void create (ParkingLot parkingLot);

  /**
   * update slot with Car.
   * @param slot slot.
   */
  public void updateSlot (Slot slot);

  /**
   * get all filled slots.
   * @return List of all filled slots.
   */
  public List<Slot> getAllFilledSlots ();

  /**
   * get nearest available slot.
   * @return slot.
   */
  public Slot getNearestAvailableSlot();

  /**
   * get slots by colour.
   * @param colour colour.
   * @return list of slots.
   */
  public List<Slot> getSlotsByColour(String colour);

  /**
   * get slots by colour.
   * @param registrationNumber registration number.
   * @return list of slots.
   */
  public Slot getSlotByRegistration(String registrationNumber);

}
