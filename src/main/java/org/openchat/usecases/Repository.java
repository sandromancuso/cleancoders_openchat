package org.openchat.usecases;

import org.openchat.entities.Document;
import org.openchat.entities.User;

import java.util.List;

public interface Repository {
  User getUser(String username);
  void addUser(User user);
  List<User> getAllUsers();
  Document getDocument(Long id);
  Long getNextDocumentId();
  void addDocument(Document document);

  class DuplicateUser extends RuntimeException {
  }
}
