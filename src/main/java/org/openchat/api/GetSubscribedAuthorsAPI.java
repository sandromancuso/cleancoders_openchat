package org.openchat.api;

import spark.Request;
import spark.Response;

public class GetSubscribedAuthorsAPI {

  public String exec(Request req, Response res) {
    res.status(200);
    res.type("application/json");
    return "";
  }
}
