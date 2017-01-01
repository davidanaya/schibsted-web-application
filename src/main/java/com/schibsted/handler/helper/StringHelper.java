package com.schibsted.handler.helper;

public class StringHelper {

  public static boolean stringContainsItemFromList(String inputString, String[] items) {
    for (int i = 0; i < items.length; i++) {
      if (inputString.contains(items[i])) {
        return true;
      }
    }
    return false;
  }

}