package com.schibsted.session;

import java.util.UUID;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class SessionsManager {

  private static SessionsManager singleton = null;
  private Map<UUID, String> users = null;
  private Map<UUID, Timer> timers = null;

  private SessionsManager() {
    users = new ConcurrentHashMap<UUID, String>();
    timers = new ConcurrentHashMap<UUID, Timer>();
    System.out.println("users and timers initiated");
  }

  public static SessionsManager getInstance() {
    if (singleton == null) {
      singleton = new SessionsManager();
    }
    return singleton;
  }

  public String createSession(String user) {
    UUID uuid = UUID.randomUUID();
    users.put(uuid, user);
    setTimeout(uuid);
    return uuid.toString();
  }

  public void deleteSession(String id) {
    users.remove(UUID.fromString(id));
    timers.remove(UUID.fromString(id));
  }

  public String getUser(String id) {
    return users.get(UUID.fromString(id));
  }

  private void setTimeout(UUID uuid) {
    Timer timer = new Timer();
    SessionTimerTask sessionTimerTask = new SessionTimerTask(uuid.toString(), singleton);
    timer.schedule(sessionTimerTask, 1000 * 60 * 5);
    timers.put(uuid, timer);
    System.out.println("Session " + uuid.toString() + " created");
  }

  public void resetTimeout(String id) {
    System.out.println("resetTimeout for " + id);
    Timer timer = timers.get(UUID.fromString(id));
    if (timer != null) {
      timer.cancel();
      timer = new Timer();
      SessionTimerTask sessionTimerTask = new SessionTimerTask(id, singleton);
      timer.schedule(sessionTimerTask, 1000 * 60 * 5);
      timers.put(UUID.fromString(id), timer);
      System.out.println("Session " + id + " reset");
    }
  }

}