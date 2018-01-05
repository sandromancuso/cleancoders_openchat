package org.openchat.domain.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class Post {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String postId;
    private final String userId;
    private String text;
    private final LocalDateTime dateTime;

    public Post(String postId, String userId, String text, LocalDateTime dateTime) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public String id() {
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
        return dateTime.toLocalDate().format(dateFormatter);
    }

    public String timeAsString() {
        return dateTime.toLocalTime().format(timeFormatter);
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

}
