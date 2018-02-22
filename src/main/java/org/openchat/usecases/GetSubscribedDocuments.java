package org.openchat.usecases;

import org.openchat.entities.Document;

import java.util.ArrayList;
import java.util.List;

public class GetSubscribedDocuments {
  public List<Document> exec(String subscriberName) {
    ArrayList<Document> documents = new ArrayList<>();
    List<String> authorNames = UseCaseContext.repository.getAuthorsSubscribedBy(subscriberName);

    return documents;
  }
}
