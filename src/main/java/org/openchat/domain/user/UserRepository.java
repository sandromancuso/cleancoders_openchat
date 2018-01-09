package org.openchat.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
        this.users.add(user);
    }

    public boolean isUsernameInUse(String username) {
        return users.stream()
                    .anyMatch(u -> u.username().equals(username));
    }

    public Optional<User> userFor(String username, String password) {
        return users.stream()
                    .filter(u -> u.username().equals(username) &&
                                 u.password().equals(password))
                    .findFirst();
    }

    public Optional<User> userForId(String userId) {
        return users.stream()
                    .filter(u -> u.userId().equals(userId))
                    .findFirst();
    }
}
