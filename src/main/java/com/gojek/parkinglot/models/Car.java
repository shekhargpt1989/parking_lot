package com.gojek.parkinglot.models;

/**
 * Class defining a Car
 */
public class Car {

  private final String colour;
  private final String registrationNumber;

  /**
   * Constructor
   * @param colour colour of car.
   * @param registrationNumber registration number of car.
   */
  public Car(String colour, String registrationNumber) {
    this.colour = colour;
    this.registrationNumber = registrationNumber;
  }


  public String getColour() {
    return colour;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Car car = (Car) o;
    return colour.equals(car.colour) &&
      registrationNumber.equals(car.registrationNumber);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((colour == null) ? 0 : colour.hashCode());
    result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
    return result;
  }

}
