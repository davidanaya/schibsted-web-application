package com.schibsted.handler;

import com.schibsted.auth.RoleAuthorisation;
import com.schibsted.data.dao.UsersDAO;
import com.schibsted.view.HandlerResponseBuilder;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class PageHandler extends BaseHandler {

  protected RoleAuthorisation authorisation = new RoleAuthorisation();
  protected UsersDAO dao = new UsersDAO();

  public void handle(HttpExchange httpExchange) throws IOException {
    super.handle(httpExchange);
    String path = httpExchange.getRequestURI().getPath();
    if (isValidSession()) {
      String username = sessionsManager.getUser(sessionId);
      handleAuthorisation(httpExchange, username, path);
    } else {
      redirect(httpExchange, "login", path.substring(1));
    } 
  }

  private void handleAuthorisation(HttpExchange httpExchange, String username, String pageName) throws IOException {
    String userRoles = dao.getUser(username).getRoles();
    int authCode = authorisation.authorise(userRoles, pageName);
    if (authCode == RoleAuthorisation.ROLE_AUTHORISED) {
      System.out.println("User " + username + " authorisation OK");
      handleAuthorised(httpExchange, username, pageName);
    } else {
      System.out.println("User " + username + " authorisation ERROR");
      handleAuthorisationError(httpExchange, authCode);
    }
  }

  private void handleAuthorised(HttpExchange httpExchange, String username, String pageName) throws IOException {
    setHeaders(httpExchange);
    StringBuilder response = HandlerResponseBuilder.buildPage(pageName, username);
    flushResponse(httpExchange, 200, response);
  }

  private void handleAuthorisationError(HttpExchange httpExchange, int authCode) throws IOException {
    switch (authCode) {
      case RoleAuthorisation.ROLE_NOT_AUTHORISED:
        setHeaders(httpExchange, "FormBased");
        StringBuilder response = HandlerResponseBuilder.build403();
        flushResponse(httpExchange, 403, response);
    }
  }

}