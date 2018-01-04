package org.openchat.core.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void add(User user);

    boolean isUsernameTaken(String username);

    Optional<User> userWithMatchingCredentials(LoginData loginData);

    Optional<User> userFor(String userId);

    void add(Following following);

    List<User> followeesFor(String userId);

    List<User> allUsers();
}
