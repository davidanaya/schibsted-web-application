package com.schibsted.app;

import com.schibsted.server.Server;
import java.io.IOException;

public class App {

  public static void main(String[] args) {
    try {
      Server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

}