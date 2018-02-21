package org.openchat.api;

import spark.Response;

class StubResponse extends Response {
  private int status = -1;
  private String type = "TILT";

  public String type() {
    return type;
  }

  public void type(String contentType) {
    type = contentType;
  }

  public void status(int statusCode) {
    status = statusCode;
  }

  public int status() {
    return status;
  }
}
