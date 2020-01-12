package com.gojek.parkinglot.handler;

import com.gojek.parkinglot.services.ParkingLotService;
import com.gojek.parkinglot.validators.ValidatorRegistry;

public class RequestHandler {

  private final ValidatorRegistry validatorRegistry;
  private final ParkingLotService parkingLotService;

  /**
   * Constructor
   * @param validatorRegistry validation registry
   * @param parkingLotService parking lot service
   */
  public RequestHandler(ValidatorRegistry validatorRegistry,
    ParkingLotService parkingLotService) {
    this.validatorRegistry = validatorRegistry;
    this.parkingLotService = parkingLotService;
  }

  /**
   * execute request
   * @param request request
   * @return
   */
  public String execute(String request) {return null;};
}
