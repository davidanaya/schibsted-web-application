package com.schibsted.app;

import com.schibsted.server.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;

public class ServerTest {

  @Before
  public void initServer() throws IOException {
    Server.start();
  }

  @After
  public void stopServer() throws IOException {
    Server.stop();
  }

  @Test
  public void testExistentPage_Ok() throws IOException, Exception {
    String url = "http://localhost:8000/page1";
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpGet httpget = new HttpGet(url);
    CloseableHttpResponse response = httpclient.execute(httpget);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
  }

  @Test
  public void testNonExistentPage_Ok() throws IOException, Exception {
    String url = "http://localhost:8000/page4";
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpGet httpget = new HttpGet(url);
    CloseableHttpResponse response = httpclient.execute(httpget);
    Assert.assertEquals(404, response.getStatusLine().getStatusCode());
  }

}