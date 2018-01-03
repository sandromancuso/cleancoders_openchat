package org.openchat.core.domain.user;

public class Follow {
    private final String followerId;
    private final String followeeId;

    public Follow(String followerId, String followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public String followerId() {
        return followerId;
    }

    public String followeeId() {
        return followeeId;
    }
}
