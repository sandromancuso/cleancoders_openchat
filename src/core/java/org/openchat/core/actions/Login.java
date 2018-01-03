package org.openchat.core.actions;

import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;

import java.util.Optional;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Login {

    public static class LoginData {
        private final String username;
        private final String password;

        public LoginData(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String username() {
            return username;
        }

        public String password() {
            return password;
        }

        @Override
        public boolean equals(Object other) {
            return reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return reflectionHashCode(this);
        }
    }

    private UserRepository userRepository;

    public Login(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(LoginData loginData) {
        return userRepository.userWithMatchingCredentials(loginData);
    }
}
