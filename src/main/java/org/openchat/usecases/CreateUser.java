package org.openchat.usecases;

import org.openchat.entities.User;

public class CreateUser {
  public User createUser(CreateUserRequest request) {
    User user = new User();
    user.username = request.username;
    user.password = request.password;
    user.about = request.about;
    return user;
  }
}
