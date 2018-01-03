package org.openchat.core.actions;

import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;
import org.openchat.core.infrastructure.IDGenerator;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class RegisterUser {

    public static class RegistrationData {
        private final String username;
        private final String password;
        private final String about;

        public RegistrationData(String username, String password, String about) {
            this.username = username;
            this.password = password;
            this.about = about;
        }

        public String username() {
            return username;
        }

        public String password() {
            return password;
        }

        public String about() {
            return about;
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

    private final IDGenerator idGenerator;
    private final UserRepository userRepository;

    public RegisterUser(IDGenerator idGenerator, UserRepository userRepository) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    public Optional<User> execute(RegistrationData registrationData) {
        if (userRepository.isUsernameTaken(registrationData.username())) {
            return empty();
        }
        
        User user = new User(idGenerator.nextId(),
                             registrationData.username(),
                             registrationData.password(),
                             registrationData.about());
        userRepository.add(user);
        return Optional.of(user);
    }
}
