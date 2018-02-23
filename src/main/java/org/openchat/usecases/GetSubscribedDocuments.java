package org.openchat.usecases;

import org.openchat.entities.Document;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

import static org.openchat.usecases.UseCaseContext.repository;

public class GetSubscribedDocuments {
  public List<Document> exec(String subscriberName) {
    ArrayList<Document> subscribedDocs = new ArrayList<>();
    List<String> authorNames = repository.getAuthorsSubscribedBy(subscriberName);
    for (String authorName : authorNames) {
      List<Document> authorDocs = repository.getDocsForUser(authorName);
      subscribedDocs.addAll(authorDocs);
    }
    List<Document> subscriberDocs = repository.getDocsForUser(subscriberName);
    subscribedDocs.addAll(subscriberDocs);

    return subscribedDocs;
  }
}
