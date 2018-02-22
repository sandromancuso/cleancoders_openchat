package org.openchat.usecases;

import org.openchat.entities.User;

import java.util.List;

public class GetUsers {
  public List<User> get() {
    return UseCaseContext.repository.getAllUsers();
  }
}
