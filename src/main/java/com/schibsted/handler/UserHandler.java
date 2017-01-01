package com.schibsted.handler;

import com.schibsted.data.dao.UsersDAO;
import com.schibsted.data.model.User;
import com.schibsted.view.HandlerResponseBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

import java.util.Scanner;

import com.google.gson.Gson;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;

public class UserHandler implements HttpHandler {

  UsersDAO dao = new UsersDAO();
  Gson gson = new Gson();

  public void handle(HttpExchange httpExchange) throws IOException {
    String method = httpExchange.getRequestMethod();
    if ("post".equalsIgnoreCase(method)) {
      createUser(httpExchange);
    } else if ("get".equalsIgnoreCase(method)) {
      readUsers(httpExchange);
    } else if ("put".equalsIgnoreCase(method)) {
      updateUser(httpExchange);
    } else if ("delete".equalsIgnoreCase(method)) {
      deleteUser(httpExchange);
    }
  }

  private void createUser(HttpExchange httpExchange) throws IOException {
    System.out.println("createUser");
    updateUser(httpExchange);
  }

  private void handleResponseUpdated(HttpExchange httpExchange) throws IOException {
    httpExchange.sendResponseHeaders(200, -1);
  }

  private void handleResponseCreated(HttpExchange httpExchange) throws IOException {
    httpExchange.sendResponseHeaders(201, -1);
  }

  private void readUsers(HttpExchange httpExchange) throws IOException {
    System.out.println("readUsers");
    String json = getJsonUsers(httpExchange);
    setHeaders(httpExchange);
    StringBuilder response = HandlerResponseBuilder.buildJson(json);
    flushResponse(httpExchange, 200, response);
  }

  private String getJsonUsers(HttpExchange httpExchange) {
    String username = getUsernameFromUrl(httpExchange.getRequestURI().getPath());
    return username != null ? gson.toJson(dao.getUser(username)) : gson.toJson(dao.getUsers());
  }

  private void updateUser(HttpExchange httpExchange) throws IOException {
    System.out.println("updateUser");
    InputStream request = httpExchange.getRequestBody();
    String json = new Scanner(request).next();
    User user = gson.fromJson(json, User.class);
    boolean updated = dao.updateUser(user);
    if (updated) {
      handleResponseUpdated(httpExchange);
    } else {
      handleResponseCreated(httpExchange);
    }
  }

  private void deleteUser(HttpExchange httpExchange) throws IOException {
    System.out.println("deleteUser");
    String username = getUsernameFromUrl(httpExchange.getRequestURI().getPath());
    dao.deleteUser(username);
    httpExchange.sendResponseHeaders(204, -1);
  }

  private void setHeaders(HttpExchange httpExchange) {
    Headers headers = httpExchange.getResponseHeaders();
    headers.set("Content-Type", "text/json");
  }

  private void flushResponse(HttpExchange httpExchange, int code, StringBuilder response) throws IOException {
    httpExchange.sendResponseHeaders(code, response.toString().length());
    OutputStream os = httpExchange.getResponseBody();
    os.write(response.toString().getBytes());
    os.close();
  }

  private String getUsernameFromUrl(String path) {
    String username = null;
    String parts[] = path.split("/users/");
    if (parts.length == 2) {
      username = parts[1].split("/")[0];
    }
    return username;
  }

}