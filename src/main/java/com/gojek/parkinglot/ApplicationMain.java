package com.gojek.parkinglot;

import com.gojek.parkinglot.dao.ParkingLotDao;
import com.gojek.parkinglot.dao.ParkingLotDaoImpl;
import com.gojek.parkinglot.handler.RequestHandler;
import com.gojek.parkinglot.services.ParkingLotService;
import com.gojek.parkinglot.services.ParkingLotServiceImpl;
import com.gojek.parkinglot.validators.ValidatorRegistry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class
 */
public class ApplicationMain {

  /**
   * main method args
   * @param args
   */
  public static void main(String[] args) {
    BufferedReader bufferReader = null;
    try {
      final ParkingLotDao parkingLotDao = new ParkingLotDaoImpl();
      final ParkingLotService parkingLotService = new ParkingLotServiceImpl(parkingLotDao);
      final RequestHandler requestHandler = new RequestHandler(ValidatorRegistry.getInstance(),
        parkingLotService);

      switch (args.length) {
        case 0: // Interactive: command-line input/output
        {
          System.out.println("Input:");
          while (true) {
            try {
              bufferReader = new BufferedReader(new InputStreamReader(System.in));
              String input = bufferReader.readLine().trim();
              if (input.equalsIgnoreCase("exit")) {
                break;
              } else {
                try {
                  System.out.println(requestHandler.execute(input.trim()));
                } catch (Exception e) {
                  System.out.println(e.getMessage());
                }
              }
            } catch (Exception e) {
            }
          }
          break;
        }
        case 1: // File: input/output
        {
          File inputFile = new File(args[0]);
          bufferReader = new BufferedReader(new FileReader(inputFile));

          int lineNo = 1;
          String input;
          while ((input = bufferReader.readLine()) != null) {
            input = input.trim();
            try {
              System.out.println(requestHandler.execute(input));
            } catch (Exception e) {
              System.out.println(e.getMessage());
            }
            lineNo++;
          }
          break;
        }
        default: {
          System.out.println("Invalid input. Use no arguments for command-line input, and only 1 argument for file input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    } finally {
      try {
        if (bufferReader != null)
          bufferReader.close();
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
