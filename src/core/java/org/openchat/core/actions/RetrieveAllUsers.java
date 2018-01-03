package org.openchat.core.actions;

import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;

import java.util.List;

public class RetrieveAllUsers {
    private UserRepository userRepository;

    public RetrieveAllUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.allUsers();
    }
}
