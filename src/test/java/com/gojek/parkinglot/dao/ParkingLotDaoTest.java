package com.gojek.parkinglot.dao;

import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.exceptions.ParkingLotException;
import com.gojek.parkinglot.models.Car;
import com.gojek.parkinglot.models.ParkingLot;
import com.gojek.parkinglot.models.Slot;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for ParkingLotDao.
 */
public class ParkingLotDaoTest {

  private ParkingLotDaoImpl parkingLotDao = new ParkingLotDaoImpl() ;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void initialize() {
    this.parkingLotDao = new ParkingLotDaoImpl();
  }


  private ParkingLot buildParkingLotWithThreeSlots(String colour1, String reg1, String colour2, String reg2, String colour3, String reg3) {
    ParkingLot parkingLot = new ParkingLot(3);
    List<Slot> slots = parkingLot.getSlots();

    if(colour1 != null && reg1 != null) {
      Slot slot1 = slots.get(0);
      final Car car1 = new Car(colour1, reg1);
      slot1.setCar(car1);
    }

    if(colour2 != null && reg2 != null) {
      Slot slot2 = slots.get(1);
      final Car car2 = new Car(colour2, reg2);
      slot2.setCar(car2);
    }

    if(colour3 != null && reg3 != null) {
      Slot slot3 = slots.get(2);
      final Car car3 = new Car(colour3, reg3);
      slot3.setCar(car3);
    }

    return parkingLot;
  }

  /**
   * create invoked once.
   */
  @Test
  public void create_invokedOnce_expectsParkingLotCreated() {
    final ParkingLot parkingLot = new ParkingLot(6);
    parkingLotDao.create(parkingLot);

    Assert.assertEquals(parkingLot, parkingLotDao.parkingLot);
  }

  /**
   * create invoked multiple times.
   */
  @Test
  public void createParkingLot_invokedMultipleTimes_throwsParkingLotAlreadyCreatedException() {
    final ParkingLot parkingLot = new ParkingLot(6);

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.PARKING_LOT_ALREADY_EXISTS.getMessage());

    parkingLotDao.create(parkingLot);
    parkingLotDao.create(parkingLot);
  }

  /**
   * add car to empty slot and return sucess.
   */
  @Test
  public void updateSlot_addCarToEmptySlot_slotShouldBeUpdate() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", null, null, "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);
    final ParkingLot expectedParkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", "Red", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");

    Slot slot = new Slot(2);
    final Car car = new Car("Red", "KA-01-HH-1235");
    slot.setCar(car);

    parkingLotDao.updateSlot(slot);

    Assert.assertEquals(expectedParkingLot, parkingLot);
  }

  /**
   * add car to invalid slot and throw exception.
   */
  @Test
  public void updateSlot_addCarToInvalidSlot_throwsInvalidSlotException() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", null, null, "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.INVALID_SLOT.getMessage());

    Slot slot = new Slot(4);
    final Car car = new Car("Red", "KA-01-HH-1235");
    slot.setCar(car);

    parkingLotDao.updateSlot(slot);

  }


  /**
   * leave car to empty a slot and return sucess.
   */
  @Test
  public void updateSlot_leaveCarToEmptyASlot_slotShouldBeUpdate() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", "Red", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);
    final ParkingLot expectedParkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", null, null, "Blue", "KA-01-HH-1236");;

    Slot slot = new Slot(2);

    parkingLotDao.updateSlot(slot);

    Assert.assertEquals(expectedParkingLot, parkingLot);
  }

  /**
   * leave car from invalid slot and throw exception.
   */
  @Test
  public void updateSlot_leaveCarfromInvalidSlot_throwsInvalidSlotException() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", null, null, "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.INVALID_SLOT.getMessage());

    Slot slot = new Slot(4);
    final Car car = new Car(null, null);
    slot.setCar(car);

    parkingLotDao.updateSlot(slot);

  }

  /**
   * update slot without creation of a Parking lot and throw exception.
   */
  @Test
  public void updateSlot_updateSlotWithoutCreating_throwsNoParkingCreatedException() {

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_PARKING_LOT_CREATED.getMessage());

    Slot slot = new Slot(3);
    final Car car = new Car(null, null);
    slot.setCar(car);

    parkingLotDao.updateSlot(slot);

  }

  /**
   * get all filled slots when parking full.
   */
  @Test
  public void getAllFilledSlots_allSlotsFilled_returnsList() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", "Red", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    final Slot expectedSlot1 = new Slot(1);
    final Car car1 = new Car("White", "KA-01-HH-1234");
    expectedSlot1.setCar(car1);

    final Slot expectedSlot2 = new Slot(2);
    final Car car2 = new Car("Red", "KA-01-HH-1235");
    expectedSlot2.setCar(car2);

    final Slot expectedSlot3 = new Slot(3);
    final Car car3 = new Car("Blue", "KA-01-HH-1236");
    expectedSlot3.setCar(car3);

    List<Slot> result = parkingLotDao.getAllFilledSlots();

    Assert.assertEquals(3, result.size());
    Assert.assertTrue(result.contains(expectedSlot1));
    Assert.assertTrue(result.contains(expectedSlot2));
    Assert.assertTrue(result.contains(expectedSlot3));
  }

  /**
   * get all filled slots when parking is empty.
   */
  @Test
  public void getAllFilledSlots_noSlotsFilled_returnsNull() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots(null, null, null, null, null, null);
    parkingLotDao.create(parkingLot);

    List<Slot> result = parkingLotDao.getAllFilledSlots();

    Assert.assertNull(result);
  }

  /**
   * get all filled slots when parking half filled.
   */
  @Test
  public void getAllFilledSlots_halfSlotsFilled_returnsList() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", null, null, null, null);
    parkingLotDao.create(parkingLot);

    final Slot expectedSlot1 = new Slot(1);
    final Car car1 = new Car("White", "KA-01-HH-1234");
    expectedSlot1.setCar(car1);

    List<Slot> result = parkingLotDao.getAllFilledSlots();

    Assert.assertEquals(1, result.size());
    Assert.assertTrue(result.contains(expectedSlot1));
  }

  /**
   * get all filled slots without creation of a Parking lot and throw exception.
   */
  @Test
  public void getAllFilledSlots_withoutCreating_throwsNoParkingCreatedException() {

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_PARKING_LOT_CREATED.getMessage());

    parkingLotDao.getAllFilledSlots();

  }

  /**
   * get nearest available slot when available.
   */
  @Test
  public void getNearestAvailableSlot_slotAvailable_returnsNearestSlot() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots(null, null, "Red", "KA-01-HH-1235", null, null);
    parkingLotDao.create(parkingLot);

    final Slot expectedSlot = new Slot(1);

    Slot result = parkingLotDao.getNearestAvailableSlot();

    Assert.assertEquals(expectedSlot, result);
  }

  /**
   * get nearest available slot when not available.
   */
  @Test
  public void getNearestAvailableSlot_slotNotAvailable_returnsNearestSlot() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", "Red", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    Slot result = parkingLotDao.getNearestAvailableSlot();

    Assert.assertNull(result);
  }

  /**
   * get nearest available slot when parking lot not created.
   */
  @Test
  public void getNearestAvailableSlot_withoutCreating_throwsNoParkingCreatedException() {

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_PARKING_LOT_CREATED.getMessage());

    parkingLotDao.getNearestAvailableSlot();
  }

  /**
   * get slots by colour when cars exist.
   */
  @Test
  public void getSlotsByColour_CarsPresent_returnsList() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", "White", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    final Slot expectedSlot1 = new Slot(1);
    final Car car1 = new Car("White", "KA-01-HH-1234");
    expectedSlot1.setCar(car1);

    final Slot expectedSlot2 = new Slot(2);
    final Car car2 = new Car("White", "KA-01-HH-1235");
    expectedSlot2.setCar(car2);

    List<Slot> result = parkingLotDao.getSlotsByColour("White");

    Assert.assertEquals(2, result.size());
    Assert.assertTrue(result.contains(expectedSlot1));
    Assert.assertTrue(result.contains(expectedSlot2));
  }

  /**
   * get slots by colour when cars don't exist.
   */
  @Test
  public void getSlotsByColour_CarsNotPresent_returnsList() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("Red", "KA-01-HH-1234", "Red", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    List<Slot> result = parkingLotDao.getSlotsByColour("White");

    Assert.assertNull(result);
  }

  /**
   * get slots when parking lot not created.
   */
  @Test
  public void getSlotsByColour_withoutCreating_throwsNoParkingCreatedException() {

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_PARKING_LOT_CREATED.getMessage());

    parkingLotDao.getSlotsByColour("White");
  }

  /**
   * get slot by registration when car exist.
   */
  @Test
  public void getSlotByRegistration_CarPresent_returnsSlot() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("White", "KA-01-HH-1234", "White", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    final Slot expectedSlot1 = new Slot(1);
    final Car car1 = new Car("White", "KA-01-HH-1234");
    expectedSlot1.setCar(car1);

    Slot result = parkingLotDao.getSlotByRegistration("KA-01-HH-1234");

    Assert.assertEquals(expectedSlot1, result);
  }

  /**
   * get slot by registration when car don't exist.
   */
  @Test
  public void getSlotByRegistration_CarNotPresent_returnsList() {
    final ParkingLot parkingLot = buildParkingLotWithThreeSlots("Red", "KA-01-HH-1234", "Red", "KA-01-HH-1235", "Blue", "KA-01-HH-1236");
    parkingLotDao.create(parkingLot);

    Slot result = parkingLotDao.getSlotByRegistration("KA-01-HH-1237");

    Assert.assertNull(result);
  }

  /**
   * get slot by registration when parking lot not created.
   */
  @Test
  public void getSlotByRegistration_withoutCreating_throwsNoParkingCreatedException() {

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_PARKING_LOT_CREATED.getMessage());

    parkingLotDao.getSlotByRegistration("KA-01-HH-1237");
  }


}
