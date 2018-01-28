package integration.dsl;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class UserDSL {

    public static class User {

        private final String id;
        private final String username;
        private final String about;
        private final String password;

        public User(String id, String username, String about, String password) {
            this.id = id;
            this.username = username;
            this.about = about;
            this.password = password;
        }

        public String id() {
            return id;
        }

        public String username() {
            return username;
        }

        public String about() {
            return about;
        }

        public String password() {
            return password;
        }

        @Override
        public boolean equals(Object other) {
            return reflectionEquals(this, other);
        }
    }

    public static class UserBuilder {

        private String userId = UUID.randomUUID().toString();
        private String username = "Alice";
        private String password = "lask3424";
        private String about = "About Alice";

        public static UserBuilder aUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(String userId) {
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

        public UserBuilder clonedFrom(User anotherUser) {
            this.userId = anotherUser.id();
            this.username = anotherUser.username();
            this.password = anotherUser.password();
            this.about = anotherUser.about();
            return this;
        }

        public User build() {
            return new User(userId, username, password, about);
        }

    }
}
