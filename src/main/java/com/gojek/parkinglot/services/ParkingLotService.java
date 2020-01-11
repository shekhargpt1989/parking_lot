package com.gojek.parkinglot.services;

import com.gojek.parkinglot.models.Car;
import com.gojek.parkinglot.models.Slot;
import com.gojek.parkinglot.models.SlotStatus;
import java.util.List;

/**
 * Contract for operations on parkinglot
 */
public interface ParkingLotService {

  /**
   * Create Parking lot.
   * @param numberOfSlots number of slots.
   */
  public void createParkingLot(int numberOfSlots);

  /**
   * Park a Car.
   * @param colour colour of Car.
   * @param registrationNumber registration number of Car.
   * @return
   */
  public int park(String colour, String registrationNumber);

  /**
   * Leave the parking lot.
   * @param slotId slot id.
   */
  public void leave(int slotId);

  /**
   * Registration numbers of cars with given colour.
   * @param colour colour of the cars.
   * @return List of Registration numbers.
   */
  public List<String> registrationsByColour(String colour);

  /**
   * Slots of cars with given colour.
   * @param colour colour of the cars.
   * @return List of Slot ids.
   */
  public List<Integer> slotIdsByColour(String colour);

  /**
   * Slot of car with registration number.
   * @param registrationNumber registration number of the cars.
   * @return Slot id.
   */
  public int slotIdByRegistration(String registrationNumber);

  /**
   * Status of parking lot.
   * @return List of slot status.
   */
  public List<SlotStatus> status();

}
