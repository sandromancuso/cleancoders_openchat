package org.openchat.domain.posts;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Post {
    private final String postId;
    private final String userId;
    private final String text;
    private final LocalDateTime dateTime;

    public Post(String postId, String userId, String text, LocalDateTime dateTime) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public String postId() {
        return postId;
    }

    public String userId() {
        return userId;
    }

    public String text() {
        return text;
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
