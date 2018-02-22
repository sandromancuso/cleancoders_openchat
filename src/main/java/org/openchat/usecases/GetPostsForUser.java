package org.openchat.usecases;

import org.openchat.entities.Document;

import java.util.List;

import static org.openchat.usecases.UseCaseContext.repository;

public class GetPostsForUser {
  public List<Document> exec(String username) {
    return repository.getDocsForUser(username);
  }
}
