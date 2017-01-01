package com.schibsted.data.model;

public class User {

  private String username;
  private String roles;
  private transient String password;

  public User(String username, String password, String roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public String getRoles() {
    return roles;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String toString() {
    return username + "#" + roles;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}