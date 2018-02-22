package org.openchat.usecases;

import org.openchat.entities.User;

import java.util.List;

public class GetUsers {
  public List<User> getAll() {
    return UseCaseContext.repository.getAllUsers();
  }
}
