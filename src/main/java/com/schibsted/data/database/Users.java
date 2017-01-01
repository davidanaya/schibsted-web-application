package com.schibsted.data.database;

import com.schibsted.data.model.User;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Users {

  private static Users singleton = null;
  private Map<String, User> users = null;

  private Users() {
    initialize();
  }

  public static Users getInstance() {
    if (singleton == null) {
      singleton = new Users();
    }
    return singleton;
  }

  private void initialize() {
    users = new HashMap<String, User>();
    insertDummyUsers();
  }

  private void insertDummyUsers() {
    User user1 = new User("user1", "user1", "PAGE_1");
    User user2 = new User("user2", "user2", "PAGE_2");
    User user3 = new User("user3", "user3", "PAGE_3");
    User superuser = new User("superuser", "superuser", "PAGE_1,PAGE_2,PAGE_3");
    User admin = new User("admin", "admin", "PAGE_1,PAGE_2,PAGE_3,ADMIN");
    users.put("user1", user1);
    users.put("user2", user2);
    users.put("user3", user3);
    users.put("superuser", superuser);
    users.put("admin", admin);
  }

  public User getUser(String username) {
    return users.get(username);
  }

  public List<User> getUsers() {
    return new ArrayList<User>(users.values());
  }

  public boolean updateUser(User user) {
    boolean updated = users.get(user.getUsername()) != null;
    if (user.getPassword() == null) {
      user.setPassword(user.getUsername());
    }
    users.put(user.getUsername(), user);
    return updated;
  }

  public void deleteUser(String username) {
    users.remove(username);
  }

}