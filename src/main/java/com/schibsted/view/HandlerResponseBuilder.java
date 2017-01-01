package com.schibsted.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.Map;

public class HandlerResponseBuilder {

  public static StringBuilder build404() {
    StringBuilder response = new StringBuilder();
    response.append("<html><body>");
    response.append("<h1>404 Not Found</h1>No context found for request.");
    response.append("</body></html>");
    return response;
  }

  public static StringBuilder build401() {
    StringBuilder response = new StringBuilder();
    response.append("<html><body>");
    response.append("<h1>401 Unauthorized</h1>Authorization has been refused for user.<br>");
    response.append("Please try to log in again with different credentials.<br>");
    response.append("<a href='http://localhost:8000/login'>login</a><br>");
    response.append("</body></html>");
    return response;
  }

  public static StringBuilder build403() {
    StringBuilder response = new StringBuilder();
    response.append("<html><body>");
    response.append("<h1>403 Forbidden</h1>Insufficient credentials to access the page for user.<br>");
    response.append("Please try to log in again with different credentials.<br>");
    response.append("<a href='http://localhost:8000/login'>login</a><br>");
    response.append("</body></html>");
    return response;
  }

  public static StringBuilder buildLogged(String username) {
    StringBuilder response = new StringBuilder();
    response.append("<html><body>");
    response.append("Hello " + username + "<br>");
    response.append("<a href='http://localhost:8000/page1'>page1</a><br>");
    response.append("<a href='http://localhost:8000/page2'>page2</a><br>");
    response.append("<a href='http://localhost:8000/page3'>page3</a><br>");
    response.append("<a href='http://localhost:8000/logout'>logout</a><br>");
    response.append("</body></html>");
    return response;
  }

  public static StringBuilder buildPage(String page, String username) {
    StringBuilder response = new StringBuilder();
    response.append("<html><body>");
    response.append("<h1>" + page + "</h1>");
    response.append("Hello " + username + "<br>");
    response.append("<a href='http://localhost:8000/logout'>logout</a>");
    response.append("</body></html>");
    return response;
  }

  public static StringBuilder buildLoginForm() {
    StringBuilder response = new StringBuilder();
    response.append("<html><body>");
    response.append("<form method=post action=\"login_form\">");
    response.append("<label><b>Username</b></label>");
    response.append("<input type=\"text\" name=\"username\">");
    response.append("<label><b>Password</b></label>");
    response.append("<input type=\"password\" name=\"password\">");
    response.append("<button type=\"submit\">Login</button>");
    response.append("</form>");
    response.append("</body></html>");
    return response;
  }

  public static StringBuilder buildJson(String json) {
    StringBuilder response = new StringBuilder();
    response.append(json);
    return response;
  }

}