package com.schibsted.handler;

import com.schibsted.view.HandlerResponseBuilder;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class LoginHandler extends BaseHandler {
  
  public void handle(HttpExchange httpExchange) throws IOException {
    super.handle(httpExchange);
    StringBuilder response = null;
    if (isValidSession()) {
      String username = sessionsManager.getUser(sessionId);
      response = HandlerResponseBuilder.buildLogged(username);
    } else {
      response = HandlerResponseBuilder.buildLoginForm();
    }
    setHeaders(httpExchange);
    flushResponse(httpExchange, 200, response);
  }

}