package com.gojek.parkinglot.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Registration number util.
 */
public class RegistrationNumberUtil {

  public static boolean isValidRegistration(String registration) {
    String[] parts = registration.split("-");

    return parts.length == 4
      && parts[0].length() == 2 && matchRegEx("[A-Za-z]*", parts[0])
      && matchRegEx("[0-9]*", parts[1])
      && matchRegEx("[A-Za-z]*", parts[2])
      && parts[3].length() == 4 && matchRegEx("[0-9]*", parts[3]);

  }

  private static boolean matchRegEx(String regex, String matchString) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(matchString);
    return matcher.matches();
  }

}
