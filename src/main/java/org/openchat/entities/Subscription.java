package org.openchat.entities;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Subscription {
  public String subscriberName;
  public String authorName;

  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  public int hashCode() {
    return reflectionHashCode(this);
  }

  public Subscription(String subscriberName, String authorName) {
    this.subscriberName = subscriberName;
    this.authorName = authorName;
  }
}
