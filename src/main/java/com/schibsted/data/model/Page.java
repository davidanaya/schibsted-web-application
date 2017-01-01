package com.schibsted.data.model;

public class Page {

  private String name;
  private String role;

  public Page(String name, String role) {
    this.name = name;
    this.role = role;
  }

  public String getRole() {
    return role;
  }

  public String getName() {
    return name;
  }

}