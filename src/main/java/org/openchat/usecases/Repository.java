package org.openchat.usecases;

import org.openchat.entities.User;

import java.util.List;

public interface Repository {
  User getUser(String username);
  void addUser(User user);
  List<User> getAllUsers();

  class DuplicateUser extends RuntimeException {
  }
}
