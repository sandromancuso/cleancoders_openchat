package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usecases.Login;
import spark.Request;
import spark.Response;

public class LoginApi {
  public String login(Request req, Response res) {
    JsonObject json = Json.parse(req.body()).asObject();
    String username = json.getString("username", "");
    String password = json.getString("password", "");
    Login login = new Login();
    User user = login.validate(username, password);
    if (user != null) {
      String body = new JsonObject()
                          .add("username", user.username)
                          .add("about", user.about)
                          .add("id", APIContext.instance.getUUIDForUser(user.username))
                          .toString();
      res.status(200);
      res.type("application/json");
      return body;
    }
    else
    {
      res.status(404);
      return "Invalid credentials.";
    }
  }
}
