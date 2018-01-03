package org.openchat.core.domain.user;

import org.openchat.core.actions.Login.LoginData;

import java.util.*;

public class UserRepository {

    private Map<String, User> users = new HashMap<>();
    private List<Follow> follows = new ArrayList();

    public void add(User user) {
        users.put(user.id(), user);
        System.out.println("user = " + user);
    }

    public boolean isUsernameTaken(String username) {
        return users.values().stream().anyMatch(u -> u.username().equals(username));
    }

    public Optional<User> userWithMatchingCredentials(LoginData loginData) {
        return users.values()
                    .stream()
                    .filter(u -> u.matches(loginData.username(), loginData.password()))
                    .findFirst();
    }

    public Optional<User> userFor(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public void addFollowing(String followerId, String followeeId) {
        follows.add(new Follow(followerId, followeeId));
    }

    public List<User> followeesFor(String userId) {
        List<User> followees = new ArrayList<>();
        follows.stream()
                .filter(follow -> follow.followerId().equals(userId))
                .map(follow -> userFor(follow.followeeId()).get())
                .forEach(followees::add);
        return followees;
    }

    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }
}
