package org.openchat.api;

import org.openchat.usecases.CreateUser;
import org.openchat.usecases.CreateUserRequest;
import spark.Request;
import spark.Response;

public class UserApi {
  public String registerUser(Request req, Response res) {
    CreateUser useCase = new CreateUser();
    CreateUserRequest useCaseRequest = new CreateUserRequest();

    useCase.createUser(useCaseRequest);

    return "stuff";
  }
}
