package com.schibsted.data.database;

import com.schibsted.data.model.Page;

import java.util.Map;
import java.util.HashMap;

public class Pages {

  private static Pages singleton = null;
  private Map<String, Page> pages = null;

  private Pages() {
    initialize();
  }

  public static Pages getInstance() {
    if (singleton == null) {
      singleton = new Pages();
    }
    return singleton;
  }

  private void initialize() {
    pages = new HashMap<String, Page>();
    insertDummyPages();
  }

  private void insertDummyPages() {
    Page page1 = new Page("/page1", "PAGE_1,ADMIN");
    Page page2 = new Page("/page2", "PAGE_2,ADMIN");
    Page page3 = new Page("/page3", "PAGE_3,ADMIN");
    Page user = new Page("/user", "ADMIN");
    pages.put("/page1", page1);
    pages.put("/page2", page2);
    pages.put("/page3", page3);
    pages.put("/user", user);
  }

  public Page getPage(String pageName) {
    return pages.get(pageName);
  }

}