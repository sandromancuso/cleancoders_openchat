package org.openchat.entities;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Document {
  public String username;
  public String text;
  public Long id;
  public LocalDateTime dateTime;

  public Document() {
  }

  public Document(String username, String text, Long id, LocalDateTime dateTime) {
    this.username = username;
    this.text = text;
    this.id = id;
    this.dateTime = dateTime;
  }

  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  public int hashCode() {
    return reflectionHashCode(this);
  }
}
