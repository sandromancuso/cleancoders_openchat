package org.openchat.usecases;

import org.openchat.entities.User;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.openchat.usecases.UseCaseContext.repository;

public class GetSubscribedAuthors {
  public List<User> exec(String subscriberName) {
    List<String> authorNames = repository.getAuthorsSubscribedBy(subscriberName);
    return authorNames.stream()
                      .map(name -> repository.getUser(name))
                      .collect(toList());
  }
}
