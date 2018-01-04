package org.openchat.core.actions;

import org.openchat.core.domain.user.Following;
import org.openchat.core.domain.user.InvalidUserException;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;

import java.util.Optional;

public class CreateFollowing {

    private UserRepository userRepository;

    public CreateFollowing(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Following following) throws InvalidUserException {
        Optional<User> follower = userRepository.userFor(following.followerId());
        Optional<User> followee = userRepository.userFor(following.followeeId());
        if (!follower.isPresent() || !followee.isPresent())
            throw new InvalidUserException();
        userRepository.add(following);
    }
}
