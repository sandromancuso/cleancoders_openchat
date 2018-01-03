package org.openchat.core.domain.user;

import org.openchat.core.actions.Login.LoginData;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void add(User user);

    boolean isUsernameTaken(String username);

    Optional<User> userWithMatchingCredentials(LoginData loginData);

    Optional<User> userFor(String userId);

    void addFollowing(String followerId, String followeeId);

    List<User> followeesFor(String userId);

    List<User> allUsers();
}
