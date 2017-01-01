package com.schibsted.data.dao;

import com.schibsted.data.database.Users;
import com.schibsted.data.model.User;

import java.util.List;

public class UsersDAO {

  private Users users;

  public UsersDAO() {
    users = Users.getInstance();
  }

  public User getUser(String username) {
    return users.getUser(username);
  }

  public List<User> getUsers() {
    return users.getUsers();
  }

  public boolean updateUser(User user) {
    return users.updateUser(user);
  }

  public void deleteUser(String username) {
    users.deleteUser(username);
  }

  public boolean hasRole(String username, String role) {
    User user = users.getUser(username);
    return user.getRoles().contains(role);
  }

}