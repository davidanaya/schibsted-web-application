package com.schibsted.server;

import com.schibsted.handler.*;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

import java.net.InetSocketAddress;

import java.io.IOException;

public class Server {
  
  private static HttpServer server;
  
  public static void start() throws IOException {

    server = HttpServer.create(new InetSocketAddress(8000), 0);
    
    server.createContext("/page1", new PageHandler());
    server.createContext("/page2", new PageHandler());
    server.createContext("/page3", new PageHandler());
    server.createContext("/login", new LoginHandler());
    server.createContext("/login_form", new LoginFormHandler());
    server.createContext("/logout", new LogoutHandler());

    HttpContext ctx = server.createContext("/users", new UserHandler());
    ctx.getFilters().add(new ApiRestRequestFilter());
    ctx.setAuthenticator(new ApiRestBasicAuthentication("users"));
    server.start();
  }

  public static void stop() throws IOException {
    server.stop(1);
  }

}