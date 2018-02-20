package org.openchat.infrastructure.builders;

import org.openchat.domain.posts.Post;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostBuilder {
    private String postId = UUID.randomUUID().toString();
    private String userId = UUID.randomUUID().toString();;
    private String text = "some text";
    private LocalDateTime dateTime = LocalDateTime.now();

    public static PostBuilder aPost() {
        return new PostBuilder();
    }

    public PostBuilder withPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public PostBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public PostBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public PostBuilder withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Post build() {
        return new Post(postId, userId, text, dateTime);
    }
}