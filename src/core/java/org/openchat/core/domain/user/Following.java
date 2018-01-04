package org.openchat.core.domain.user;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class Following {

    private final String followerId;
    private final String followeeId;

    public Following(String followerId, String followeeId) {
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
}
