package org.openchat.entities;

import java.time.LocalDateTime;

public class DocumentBuilder {
  private String username = "username";
  private String text = "text";
  private long id = 0L;
  private LocalDateTime dateTime = LocalDateTime.now();

  public static DocumentBuilder aDoc() {
    return new DocumentBuilder();
  }

  public DocumentBuilder setUsername(String username) {
    this.username = username;
    return this;
  }

  public DocumentBuilder setText(String text) {
    this.text = text;
    return this;
  }

  public DocumentBuilder setId(long id) {
    this.id = id;
    return this;
  }

  public DocumentBuilder setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public Document build() {
    return new Document(username, text, id, dateTime);
  }
}