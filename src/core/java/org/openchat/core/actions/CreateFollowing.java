package org.openchat.core.actions;

import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;

import java.util.Optional;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class CreateFollowing {

    private UserRepository userRepository;

    public CreateFollowing(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static class FollowingData {

        private final String followerId;
        private final String followeeId;

        public FollowingData(String followerId, String followeeId) {
            this.followerId = followerId;
            this.followeeId = followeeId;
        }

        public String followerId() {
            return followerId;
        }

        public String followeeId() {
            return followeeId;
        }

        @Override
        public boolean equals(Object other) {
            return reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return reflectionToString(this, MULTI_LINE_STYLE);
        }
    }

    public static class InvalidUserException extends RuntimeException {
    }

    public void execute(FollowingData followingData) throws InvalidUserException {
        Optional<User> follower = userRepository.userFor(followingData.followerId);
        Optional<User> followee = userRepository.userFor(followingData.followeeId);
        if (!follower.isPresent() || !followee.isPresent())
            throw new InvalidUserException();
        userRepository.addFollowing(followingData.followerId(), followingData.followeeId() );
    }
}
