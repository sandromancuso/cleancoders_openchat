package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;
import org.openchat.usecases.CreateUser;
import org.openchat.usecases.CreateUserRequest;
import org.openchat.usecases.Repository;
import spark.Request;
import spark.Response;

public class RegisterUserAPI {
  public String exec(Request req, Response res) {
    CreateUserRequest createUserRequest = makeCreateUserRequest(req.body());
    CreateUser useCase = new CreateUser();

    try {
      User user = useCase.createUser(createUserRequest);
      APIContext.instance.makeUUIDForUser(user.username);
      return makeCreatedUserResponse(user, res);
    } catch (Repository.DuplicateUser e) {
      return makeDuplicateUserResponse(res);
    }
  }

  protected String makeDuplicateUserResponse(Response res) {
    res.status(400);
    return "Username already in use.";
  }

  protected String makeCreatedUserResponse(User user, Response res) {
    res.status(201);
    res.type("application/json");

    String body = new JsonObject()
                        .add("username", user.username)
                        .add("about", user.about)
                        .add("id", APIContext.instance.getUUIDForUser(user.username))
                        .toString();
    return body;
  }

  protected CreateUserRequest makeCreateUserRequest(String jsonString) {
    CreateUserRequest request = new CreateUserRequest();
    JsonObject json = Json.parse(jsonString).asObject();
    request.username = json.getString("username", null);
    request.password = json.getString("password", null);
    request.about = json.getString("about", null);
    return request;
  }
}
