package integration.dsl;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class UserDSL {

    public static class ITUser {

        private final String id;
        private final String username;
        private final String about;
        private final String password;

        ITUser(String id, String username, String password, String about) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.about = about;
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

        @Override
        public String toString() {
            return reflectionToString(this, MULTI_LINE_STYLE);
        }
    }

    public static class ITUserBuilder {

        private String userId = UUID.randomUUID().toString();
        private String username = "Alice";
        private String password = "lask3424";
        private String about = "About Alice";

        public static ITUserBuilder aUser() {
            return new ITUserBuilder();
        }

        public ITUserBuilder withId(String userId) {
            this.userId = userId;
            return this;
        }

        public ITUserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public ITUserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public ITUserBuilder withAbout(String about) {
            this.about = about;
            return this;
        }

        public ITUserBuilder clonedFrom(ITUser anotherUser) {
            this.userId = anotherUser.id();
            this.username = anotherUser.username();
            this.password = anotherUser.password();
            this.about = anotherUser.about();
            return this;
        }

        public ITUser build() {
            return new ITUser(userId, username, password, about);
        }

    }
}
