package org.openchat.entities;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class User {
  public String username;
  public String password;
  public String about;

  public boolean equals(Object other) {
    return reflectionEquals(this, other);
  }

  public int hashCode() {
    return reflectionHashCode(this);
  }
}
