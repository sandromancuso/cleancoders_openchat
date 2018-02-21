package org.openchat.usecases;

import org.openchat.entities.User;

public interface Repository {
  User getUser(String username);
  void addUser(User user);

  class DuplicateUser extends RuntimeException {
  }
}
