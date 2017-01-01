package com.schibsted.handler;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;
import java.util.Arrays;

import com.schibsted.session.SessionsManager;
import com.schibsted.handler.helper.CookieHelper;
import com.schibsted.auth.UserAuthentication;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;

public abstract class BaseHandler implements HttpHandler {
  
  protected UserAuthentication authentication = new UserAuthentication();
  protected SessionsManager sessionsManager = SessionsManager.getInstance();

  protected String sessionId = null;
  protected String redirect = null;

  public void handle(HttpExchange httpExchange) throws IOException {
    updateSessionIdFromHeader(httpExchange);
    if (sessionId != null) {
      resetSessionTimeout();
    }
    updateRedirectFromHeader(httpExchange);
  }

  protected void setHeaders(HttpExchange httpExchange) {
    setHeaders(httpExchange, null);
  }

  protected void setHeaders(HttpExchange httpExchange, String WWWAuthenticate) {
    Headers headers = httpExchange.getResponseHeaders();
    if (WWWAuthenticate != null) {
      headers.set("WWW-Authenticate", WWWAuthenticate);
    }
    setSessionIdInHeader(httpExchange);
    setRedirectInHeader(httpExchange);
    headers.set("Content-Type", "text/html");
  }

  private void resetSessionTimeout() {
    sessionsManager.resetTimeout(sessionId);
  }

  private void setSessionIdInHeader(HttpExchange httpExchange) {
    if (sessionId != null) {
      httpExchange.getResponseHeaders().add("Set-Cookie", "schibsted=" + sessionId);
    }
  }

  private void setRedirectInHeader(HttpExchange httpExchange) {
    if (redirect != null) {
      httpExchange.getResponseHeaders().add("Set-Cookie", "redirect=" + redirect);
    }
  }

  private void updateSessionIdFromHeader(HttpExchange httpExchange) {
    List<String> cookies = httpExchange.getRequestHeaders().get("Cookie");    
    if (cookies != null) {
      sessionId = CookieHelper.getCookieValue(cookies.get(0), "schibsted");
    }
  }

  private void updateRedirectFromHeader(HttpExchange httpExchange) {
    List<String> cookies = httpExchange.getRequestHeaders().get("Cookie");    
    if (cookies != null) {
      redirect = CookieHelper.getCookieValue(cookies.get(0), "redirect");
    }
  }

  protected void flushResponse(HttpExchange httpExchange, int code, StringBuilder response) throws IOException {
    httpExchange.sendResponseHeaders(code, response.toString().length());
    OutputStream os = httpExchange.getResponseBody();
    os.write(response.toString().getBytes());
    os.close();
  }

  protected void redirect(HttpExchange httpExchange, String page) throws IOException {
    redirect(httpExchange, page, null);
  }

  protected void redirect(HttpExchange httpExchange, String page, String redirect) throws IOException {
    httpExchange.getResponseHeaders().add("Location", "http://localhost:8000/" + page);
    this.redirect = redirect;    
    setSessionIdInHeader(httpExchange); 
    setRedirectInHeader(httpExchange); 
    httpExchange.sendResponseHeaders(302, 0);
    httpExchange.close();
  }

  protected boolean isValidSession() {
    return sessionId != null && sessionsManager.getUser(sessionId) != null;
  }

}