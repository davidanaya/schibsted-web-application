package com.schibsted.server;

import com.schibsted.auth.UserAuthentication;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpHandler;

public class ApiRestBasicAuthentication extends BasicAuthenticator {

  private UserAuthentication authentication = new UserAuthentication();

  public ApiRestBasicAuthentication(String realm) {
    super(realm);
  }

  @Override
  public boolean checkCredentials(String user, String pwd) {
    int authCode = authentication.authenticate(user, pwd);
    return authCode == UserAuthentication.USER_AUTHENTICATED;
  }

}