package org.openchat.repositories;

import org.openchat.entities.Document;
import org.openchat.entities.Subscription;
import org.openchat.entities.User;
import org.openchat.usecases.Repository;

import javax.print.Doc;
import java.util.*;

public class InMemoryRepository implements Repository {
  private Map<String, User> users = new HashMap<>();
  private Map<Long, Document> documents = new HashMap<>();
  private List<Subscription> subscriptions = new ArrayList<>();
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
    copy.dateTime = document.dateTime;

    documents.put(document.id, copy);
  }

  public List<Document> getDocsForUser(String username) {
    Collection<Document> allDocs = documents.values();
    List<Document> result = new ArrayList<>();
    for (Document d : allDocs) {
      if (d.username.equals(username))
        result.add(d);
    }
    return  result;
  }

  public boolean follows(String subscriberName, String authorName) {
    return subscriptions.contains(new Subscription(subscriberName, authorName));
  }

  public void addSubscription(String subscriberName, String authorName) {
    subscriptions.add(new Subscription(subscriberName, authorName));
  }
}
