package org.openchat.domain.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Post {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

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

    public String dateTimeAsString() {
        return dateTime.format(dateTimeFormatter);
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
