package org.openchat.usecases;

import org.openchat.entities.Document;

public class PostDocument {
  public Document post(String username, String text) {
    Document document = new Document();
    document.username = username;
    document.text = text;
    document.id = UseCaseContext.repository.getNextDocumentId();
    UseCaseContext.repository.addDocument(document);
    return document;
  }

  public Document postOnlyAppropriateDocument(String username, String text) {
    String lowerText = text.toLowerCase();
    if (lowerText.contains("orange") || lowerText.contains("ice cream") || lowerText.contains("elephant"))
      throw new InappropriateException();
    return post(username, text);
  }

  public class InappropriateException extends RuntimeException {
  }
}
