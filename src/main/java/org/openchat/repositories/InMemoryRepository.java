package org.openchat.repositories;

import org.openchat.entities.Document;
import org.openchat.entities.User;
import org.openchat.usecases.Repository;

import java.util.*;

public class InMemoryRepository implements Repository {
  private Map<String, User> users = new HashMap<>();
  private Map<Long, Document> documents = new HashMap<>();
  private Long nextId = 0L;

  public User getUser(String username) {
    return users.get(username);
  }

  public void addUser(User user) {
    User copy = new User();
    copy.username = user.username;
    copy.password = user.password;
    copy.about = user.about;
    users.put(user.username, copy);
  }

  public List<User> getAllUsers() {
    ArrayList<User> theUsers = new ArrayList<>(this.users.values());
    return Collections.unmodifiableList(theUsers);
  }

  public Document getDocument(Long id) {
    return documents.get(id);
  }

  public Long getNextDocumentId() {
    return nextId++;
  }

  public void addDocument(Document document) {
    Document copy = new Document();
    copy.username = document.username;
    copy.text = document.text;
    copy.id = document.id;

    documents.put(document.id, copy);
  }
}
