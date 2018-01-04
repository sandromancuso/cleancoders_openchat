package org.openchat.core.domain.user;

import java.util.*;

public class UserRepositoryInMemory implements UserRepository {

    private Map<String, User> users = new HashMap<>();
    private List<Following> followings = new ArrayList();

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

    public void add(Following following) {
        followings.add(following);
    }

    public List<User> followeesFor(String userId) {
        List<User> followees = new ArrayList<>();
        followings.stream()
                .filter(follow -> follow.followerId().equals(userId))
                .map(follow -> userFor(follow.followeeId()).get())
                .forEach(followees::add);
        return followees;
    }

    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }
}
