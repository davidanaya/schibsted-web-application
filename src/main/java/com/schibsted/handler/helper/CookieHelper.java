package com.schibsted.handler.helper;

public class CookieHelper {

  public static String getCookieValue(String cookies, String name) {
    String cookieValue = null;
    if (cookies != null) {
      String parts[] = cookies.split(name + "=");
      if (parts.length == 2) {
        cookieValue = parts[1].split(";")[0];
      }
    }
    return cookieValue;
  }

}