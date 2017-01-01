package com.schibsted.handler.helper;

import java.util.Map;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class HandlerQueryParser {
  
  public static Map<String, String> parseQuery(InputStream inputStream) {

    Map<String, String> parameters = new HashMap<String, String>();

    try {
      InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
      BufferedReader br = new BufferedReader(isr);
      String query = br.readLine();

      if (query != null) {
        String pairs[] = query.split("[&]");

        for (String pair : pairs) {
          String param[] = pair.split("[=]");

          String key = null;
          String value = null;
          if (param.length > 0) {
            key = param[0];
          }

          if (param.length > 1) {
            value = param[1];
          }

          parameters.put(key, value);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return parameters;
  }

}