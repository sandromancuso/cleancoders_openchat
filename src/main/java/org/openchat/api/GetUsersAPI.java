package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usecases.GetUsers;
import spark.Request;
import spark.Response;

import java.util.List;

public class GetUsersAPI {
  public String getUsers(Request req, Response res) {
    GetUsers getUsers = new GetUsers();
    List<User> users = getUsers.getAll();
    JsonArray userArray = new JsonArray();
    for (User u : users) {
      JsonObject user = new JsonObject()
                          .add("id", APIContext.instance.getUUIDForUser(u.username))
                          .add("username", u.username)
                          .add("about", u.about);

      userArray.add(user);
    }
    res.status(200);
    res.type("application/json");
    return userArray.toString();
  }
}
