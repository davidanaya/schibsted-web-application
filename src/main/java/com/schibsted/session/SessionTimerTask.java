package com.schibsted.session;

import java.util.TimerTask;

public class SessionTimerTask extends TimerTask {

  private final String id;
  private SessionsManager sessionsManager;

  SessionTimerTask(String id, SessionsManager sessionsManager) {
    this.id = id;
    this.sessionsManager = sessionsManager;
  }

  public void run() {
    System.out.println("Session " + id + " removed");
    sessionsManager.deleteSession(id);
  }
}