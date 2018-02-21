package org.openchat.entities;

public class UserBuilder {
  private String username = "username";
  private String password = "password";
  private String about = "about";

  public static UserBuilder aUser() {return new UserBuilder();}

  public UserBuilder setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder setAbout(String about) {
    this.about = about;
    return this;
  }

  public User build() {
    return new User(username, password, about);
  }
}