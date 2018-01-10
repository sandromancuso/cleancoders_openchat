package org.openchat.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class UserRepository {

    private List<User> users = new ArrayList<>();
    private List<Follow> followings = new ArrayList<>();

    private class Follow {

        private final User follower;

        private final User followee;
        Follow(User follower, User followee) {
            this.follower = follower;
            this.followee = followee;
        }

    }

    public List<User> all() {
        return unmodifiableList(users);
    }

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

    public void createFollowing(User follower, User followee) {
        followings.add(follow(follower, followee));
    }

    public List<User> followeesFor(String userId) {
        return followings.stream()
                            .filter(follow -> follow.follower.userId().equals(userId))
                            .map(follow -> userForId(follow.followee.userId()).get())
                            .collect(toList());
    }

    private Follow follow(User follower, User followee) {
        return new Follow(follower, followee);
    }
}
