package org.openchat.infrastructure.builders;

import org.openchat.domain.users.User;

import java.util.UUID;

public class UserBuilder {
    private String id = UUID.randomUUID().toString();
    private String username = "Username";
    private String password = "password";
    private String about = "about";

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withAbout(String about) {
        this.about = about;
        return this;
    }

    public User build() {
        return new User(id, username, password, about);
    }
}