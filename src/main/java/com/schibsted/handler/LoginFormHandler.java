package com.schibsted.handler;

import com.schibsted.handler.helper.*;
import com.schibsted.auth.UserAuthentication;
import com.schibsted.view.HandlerResponseBuilder;

import java.io.IOException;

import java.util.Map;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public class LoginFormHandler extends BaseHandler {

  public void handle(HttpExchange httpExchange) throws IOException {
    super.handle(httpExchange);
    if ("post".equalsIgnoreCase(httpExchange.getRequestMethod())) {
      handlePost(httpExchange);
    } else {
      handleGet(httpExchange);
    }
  }

  private void handlePost(HttpExchange httpExchange) throws IOException {
    Map<String, String> params = HandlerQueryParser.parseQuery(httpExchange.getRequestBody());
    String username = params.get("username");
    String password = params.get("password");
    int authCode = authentication.authenticate(username, password);
    if (authCode == UserAuthentication.USER_AUTHENTICATED) {
      System.out.println("User " + username + " authentication OK");
      handleAuthenticated(httpExchange, username);
    } else {
      System.out.println("User " + username + " authentication ERROR " + authCode);
      handleAuthenticationError(httpExchange, authCode);
    }
  }

  private void handleAuthenticated(HttpExchange httpExchange, String username) throws IOException {
    sessionId = sessionsManager.createSession(username);
    if (redirect != null) {
      handleWithRedirection(httpExchange, username);
    } else {
      handleWithNoRedirection(httpExchange, username);
    }
  }

  private void handleGet(HttpExchange httpExchange) throws IOException {
    setHeaders(httpExchange);
    StringBuilder response = HandlerResponseBuilder.build404();
    flushResponse(httpExchange, 404, response);
  }

  private void handleWithRedirection(HttpExchange httpExchange, String username) throws IOException {
    redirect(httpExchange, redirect);
  }

  private void handleWithNoRedirection(HttpExchange httpExchange, String username) throws IOException {
    setHeaders(httpExchange);
    StringBuilder response = HandlerResponseBuilder.buildLogged(username);
    flushResponse(httpExchange, 200, response);
  }

  private void handleAuthenticationError(HttpExchange httpExchange, int authCode) throws IOException {
    switch (authCode) {
      case UserAuthentication.USER_NOT_EXISTS:
      case UserAuthentication.PASSWORD_NOT_MATCH:
      case UserAuthentication.INVALID_CREDENTIALS:
        setHeaders(httpExchange, "FormBased");
        StringBuilder response = HandlerResponseBuilder.build401();
        flushResponse(httpExchange, 401, response);
    }
  }

}