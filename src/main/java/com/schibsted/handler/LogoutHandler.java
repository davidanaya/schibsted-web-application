package com.schibsted.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class LogoutHandler extends BaseHandler {
  
  public void handle(HttpExchange httpExchange) throws IOException {
    super.handle(httpExchange);
    sessionsManager.deleteSession(sessionId);
    redirect(httpExchange, "login");
  }

}