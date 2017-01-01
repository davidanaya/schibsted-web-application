package com.schibsted.server;

import com.schibsted.view.HandlerResponseBuilder;
import com.schibsted.data.dao.UsersDAO;
import com.schibsted.data.model.User;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Base64;

import java.nio.charset.Charset;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class ApiRestRequestFilter extends Filter {

  private static final String FILTER_DESCRIPTION = 
    "ApiRestRequestFilter checks the authorisation privileges of the user to process POST, PUT and DELETE requests";

  private UsersDAO dao = new UsersDAO();

  @Override
  public String description() {
    return FILTER_DESCRIPTION;
  }

  @Override
  public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
    if (!needsAuthorisation(httpExchange) || isAuthorised(getUsername(httpExchange))) {
      chain.doFilter(httpExchange);
    } else {
      System.out.println("not authorised");
      StringBuilder response = HandlerResponseBuilder.build401();
      httpExchange.sendResponseHeaders(401, response.toString().length());
      OutputStream os = httpExchange.getResponseBody();
      os.write(response.toString().getBytes());
      os.close();
    }
  }

  private boolean needsAuthorisation(HttpExchange httpExchange) {
    return !"get".equalsIgnoreCase(httpExchange.getRequestMethod());
  }

  private boolean isAuthorised(String username) {
    User user = dao.getUser(username);
    return user.getRoles().contains("ADMIN");
  }

  private String getUsername(HttpExchange httpExchange) {
    String authorization = httpExchange.getRequestHeaders().get("Authorization").get(0);
    if (authorization != null && authorization.startsWith("Basic")) {
      String base64Credentials = authorization.substring("Basic".length()).trim();
      String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
      String[] values = credentials.split(":", 2);
      return values[0];
    }
    return null;
  }

}