package org.openchat.usecases;

import org.openchat.entities.User;

public class CreateUser {
  public User createUser(CreateUserRequest request) {
    if (UseCaseContext.repository.getUser(request.username) != null)
      throw new Repository.DuplicateUser();
    User user = makeUser(request);
    UseCaseContext.repository.addUser(user);
    return user;
  }

  private User makeUser(CreateUserRequest request) {
    User user = new org.openchat.entities.UserBuilder().createUser();
    user.username = request.username;
    user.password = request.password;
    user.about = request.about;
    return user;
  }
}
