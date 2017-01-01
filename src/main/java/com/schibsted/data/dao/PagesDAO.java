package com.schibsted.data.dao;

import com.schibsted.data.database.Pages;
import com.schibsted.data.model.Page;

public class PagesDAO {

  private Pages pages;

  public PagesDAO() {
    pages = Pages.getInstance();
  }

  public String getRole(String pageName) {
    Page page = pages.getPage(pageName);
    return page.getRole();
  }

}