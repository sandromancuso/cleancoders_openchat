package org.openchat.entities;

import javax.lang.model.element.NestingKind;

public class User {
  public String username;
  public String password;
  public String about;

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (username != null ? !username.equals(user.username) : user.username != null) return false;
    if (password != null ? !password.equals(user.password) : user.password != null) return false;
    return about != null ? about.equals(user.about) : user.about == null;
  }

  public int hashCode() {
    int result = username != null ? username.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (about != null ? about.hashCode() : 0);
    return result;
  }
}
