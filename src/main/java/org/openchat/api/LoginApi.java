package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.usecases.Login;
import spark.Request;
import spark.Response;

public class LoginApi {
  public String login(Request req, Response res) {
    JsonObject json = Json.parse(req.body()).asObject();
    String username = json.getString("username", "");
    String password = json.getString("password", "");
    Login login = new Login();
    if (login.validate(username, password)) {
      return "";
    }
    else
    {
      res.status(404);
      return "Invalid credentials.";
    }
  }
}
