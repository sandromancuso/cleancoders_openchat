package org.openchat.core.actions;

import org.openchat.core.domain.user.LoginData;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;

import java.util.Optional;

public class Login {

    private UserRepository userRepository;

    public Login(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(LoginData loginData) {
        return userRepository.userWithMatchingCredentials(loginData);
    }
}
