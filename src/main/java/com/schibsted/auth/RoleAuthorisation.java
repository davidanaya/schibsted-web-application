package com.schibsted.auth;

import com.schibsted.data.dao.PagesDAO;
import com.schibsted.data.model.Page;
import com.schibsted.handler.helper.StringHelper;

public class RoleAuthorisation {

  public static final int INVALID_PAGE = 0;
  public static final int ROLE_NOT_AUTHORISED = 1;
  public static final int ROLE_AUTHORISED = 2;

  private PagesDAO dao;

  public RoleAuthorisation() {
    dao = new PagesDAO();
  }

  public int authorise(String roles, String pageName) {
    String rolesAllowed = dao.getRole(pageName);
    if (rolesAllowed == null) {
      return INVALID_PAGE;
    }

    if (!StringHelper.stringContainsItemFromList(roles, rolesAllowed.split(","))) {
      return ROLE_NOT_AUTHORISED;
    }
    
    return ROLE_AUTHORISED;
  }

}