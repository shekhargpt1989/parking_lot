package com.gojek.parkinglot.services;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gojek.parkinglot.dao.ParkingLotDao;
import com.gojek.parkinglot.exceptions.ErrorCode;
import com.gojek.parkinglot.exceptions.ParkingLotException;
import com.gojek.parkinglot.models.Car;
import com.gojek.parkinglot.models.ParkingLot;
import com.gojek.parkinglot.models.Slot;
import com.gojek.parkinglot.models.SlotStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for ParkingLotService
 */
public class ParkingLotServiceTest {

  @Mock
  private ParkingLotDao parkingLotDao;

  private ParkingLotServiceImpl parkingLotService;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();


  @Before
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    parkingLotService = new ParkingLotServiceImpl(parkingLotDao);
  }

  /**
   * create invoked once.
   */
  @Test
  public void createParkingLot_invokedOnce_expectsParkingLotCreated() {
    parkingLotService.createParkingLot(6);
    verify(parkingLotDao, Mockito.times(1)).create(Mockito.<ParkingLot>anyObject());
  }

  /**
   * park with success.
   */
  @Test
  public void park_withParams_expectsSlotId() {
    when(parkingLotDao.getNearestAvailableSlot()).thenReturn(new Slot(1));

    int slotId = parkingLotService.park("White", "KA-01-HH-1234");
    verify(parkingLotDao, Mockito.times(1)).updateSlot(Mockito.<Slot>anyObject());

    Assert.assertEquals(1, slotId);
  }

  /**
   * park with failure.
   */
  @Test
  public void park_withParams_throwsSlotNotAvailableException() {
    when(parkingLotDao.getNearestAvailableSlot()).thenReturn(null);

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.PARKING_ALREADY_FULL.getMessage());

    parkingLotService.park("White", "KA-01-HH-1234");
  }

  /**
   * leave with success.
   */
  @Test
  public void leave_withParams_expectsSucess() {
    parkingLotService.leave(1);
    verify(parkingLotDao, Mockito.times(1)).updateSlot(Mockito.<Slot>anyObject());
  }

  /**
   * leave with failure.
   */
  @Test
  public void leave_withParams_throwsInvalidSlotException() {
    doThrow(new ParkingLotException(ErrorCode.INVALID_SLOT)).when(parkingLotDao).updateSlot(Mockito.<Slot>anyObject());

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.INVALID_SLOT.getMessage());

    parkingLotService.leave(10);
  }

  /**
   * Registrations by colour with success.
   */
  @Test
  public void registrationsByColour_withParams_expectsList() {

    final Slot slot1 = new Slot(1);
    final Car car1 = new Car("White", "KA-01-HH-1234");
    slot1.setCar(car1);

    final Slot slot2 = new Slot(3);
    final Car car2 = new Car("White", "KA-01-HH-1235");
    slot2.setCar(car2);

    final List<Slot> slots = new ArrayList<>();
    slots.add(slot1);
    slots.add(slot2);

    when(parkingLotDao.getSlotsByColour("White")).thenReturn(slots);

    final List<String> registrations = parkingLotService.registrationsByColour("White");

    Assert.assertEquals(2, registrations.size());
    Assert.assertTrue(registrations.contains("KA-01-HH-1234"));
    Assert.assertTrue(registrations.contains("KA-01-HH-1235"));
  }

  /**
   * Registrations by colour when no such cars found.
   */
  @Test
  public void registrationsByColour_withParams_throwsNoSuchCarsFoundException() {

    when(parkingLotDao.getSlotsByColour("White")).thenReturn(null);

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_SUCH_CARS_FOUND.getMessage());

    parkingLotService.registrationsByColour("White");

  }

  /**
   * Slot by registration with success.
   */
  @Test
  public void slotIdByRegistration_withParams_expectsISlotId() {

    final Slot slot = new Slot(1);
    final Car car = new Car("White", "KA-01-HH-1234");
    slot.setCar(car);

    when(parkingLotDao.getSlotByRegistration("KA-01-HH-1234")).thenReturn(slot);

    final int result = parkingLotService.slotIdByRegistration("KA-01-HH-1234");

    Assert.assertEquals(1, result);
  }

  /**
   * Registrations by colour when no such cars found.
   */
  @Test
  public void slotIdByRegistration_withParams_throwsNoSuchCarsFoundException() {

    when(parkingLotDao.getSlotByRegistration("KA-01-HH-1234")).thenReturn(null);

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_SUCH_CARS_FOUND.getMessage());

    parkingLotService.slotIdByRegistration("KA-01-HH-1234");

  }

  /**
   * Slot Ids by colour with success.
   */
  @Test
  public void slotIdsByColour_withParams_expectsList() {

    final Slot slot1 = new Slot(1);
    final Car car1 = new Car("White", "KA-01-HH-1234");
    slot1.setCar(car1);

    final Slot slot2 = new Slot(3);
    final Car car2 = new Car("White", "KA-01-HH-1235");
    slot2.setCar(car2);

    final List<Slot> slots = new ArrayList<>();
    slots.add(slot1);
    slots.add(slot2);

    when(parkingLotDao.getSlotsByColour("White")).thenReturn(slots);

    final List<Integer> slotIds = parkingLotService.slotIdsByColour("White");

    Assert.assertEquals(2, slotIds.size());
    Assert.assertTrue(slotIds.contains(1));
    Assert.assertTrue(slotIds.contains(3));
  }

  /**
   * Slot Ids by colour when no such cars found.
   */
  @Test
  public void slotIdsByColour_withParams_throwsNoSuchCarsFoundException() {

    when(parkingLotDao.getSlotsByColour("White")).thenReturn(null);

    exceptionRule.expect(ParkingLotException.class);
    exceptionRule.expectMessage(ErrorCode.NO_SUCH_CARS_FOUND.getMessage());

    parkingLotService.registrationsByColour("White");

  }

  /**
   * Status of the Parking lot with cars.
   */
  @Test
  public void status_withCars_shouldReturnNonEmptySlotStatusList() {

    final Slot slot1 = new Slot(1);
    final Car car1 = new Car("White", "KA-01-HH-1234");
    slot1.setCar(car1);

    final Slot slot2 = new Slot(3);
    final Car car2 = new Car("White", "KA-01-HH-1235");
    slot2.setCar(car2);

    final List<Slot> slots = new ArrayList<>();
    slots.add(slot1);
    slots.add(slot2);

    final SlotStatus expectedSlotStatus1 = new SlotStatus(1, "White", "KA-01-HH-1234");
    final SlotStatus expectedSlotStatus2 = new SlotStatus(3, "White", "KA-01-HH-1235");

    when(parkingLotDao.getAllFilledSlots()).thenReturn(slots);

    final List<SlotStatus> slotStatuses = parkingLotService.status();

    Assert.assertEquals(2, slotStatuses.size());
    Assert.assertTrue(slotStatuses.contains(expectedSlotStatus1));
    Assert.assertTrue(slotStatuses.contains(expectedSlotStatus2));

  }

  /**
   * Status of the Parking lot without any cars.
   */
  @Test
  public void status_withCars_shouldReturnEmptyList() {

    when(parkingLotDao.getAllFilledSlots()).thenReturn(null);

    final List<SlotStatus> slotStatuses = parkingLotService.status();

    Assert.assertEquals(0, slotStatuses.size());

  }


}
