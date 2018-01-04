package org.openchat.domain.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
<<<<<<< HEAD:src/main/java/org/openchat/domain/post/Post.java
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
=======
>>>>>>> User and Post services added but not used:src/core/java/org/openchat/core/domain/post/Post.java

public class Post {
    private static DateTimeFormatter dateFormatter = ofPattern("dd/MM/yyyy");
    private static DateTimeFormatter timeFormatter = ofPattern("HH/mm/ss");

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

    public String dateAsString() {
        return dateTime.format(dateFormatter);
    }

    public String timeAsString() {
        return dateTime.format(timeFormatter);
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

<<<<<<< HEAD:src/main/java/org/openchat/domain/post/Post.java
    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
=======
>>>>>>> User and Post services added but not used:src/core/java/org/openchat/core/domain/post/Post.java
}
