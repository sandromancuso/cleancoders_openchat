package org.openchat.domain.users;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public boolean isUsernameTaken(String username) {
        return users.stream()
                    .anyMatch(user -> user.username().equals(username));
    }
}
