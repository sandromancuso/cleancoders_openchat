package org.openchat.usecases;

import org.openchat.entities.User;

public interface Repository {
  public User getUser(String username);
  User addUser(User user);
}
