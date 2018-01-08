package org.openchat.domain.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
        this.users.add(user);
    }

    public boolean isUsernameInUse(String username) {
        return users.stream()
                    .anyMatch(u -> u.username().equals(username));
    }
}
