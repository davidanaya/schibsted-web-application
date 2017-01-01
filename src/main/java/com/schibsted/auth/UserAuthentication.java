package com.schibsted.auth;

import com.schibsted.data.dao.UsersDAO;
import com.schibsted.data.model.User;

public class UserAuthentication {

  public static final int USER_NOT_EXISTS = 0;
  public static final int PASSWORD_NOT_MATCH = 1;
  public static final int USER_AUTHENTICATED = 2;
  public static final int INVALID_CREDENTIALS = 3;

  private UsersDAO dao;

  public UserAuthentication() {
    dao = new UsersDAO();
  }

  public int authenticate(String username, String password) {
    
    if (!isValidCredentials(username, password)) {
      return INVALID_CREDENTIALS;
    }

    User user = dao.getUser(username);
    if (user == null) {
      return USER_NOT_EXISTS;
    } 
    
    if (!isPasswordMatch(password, user)) {
      return PASSWORD_NOT_MATCH;
    }
    
    return USER_AUTHENTICATED;
  }

  private boolean isValidCredentials(String username, String password) {
    return username != null && password != null;
  }

  private boolean isPasswordMatch(String password, User user) {
    return password.equals(user.getPassword());
  }

}