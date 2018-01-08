package org.openchat.domain.user;

public class UserBuilder {
    private String userId = "1234245";
    private String username = "Alice";
    private String password = "lask3424";
    private String about = "About Alice";

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withUserId(String userId) {
        this.userId = userId;
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
        return new User(userId, username, password, about);
    }
}