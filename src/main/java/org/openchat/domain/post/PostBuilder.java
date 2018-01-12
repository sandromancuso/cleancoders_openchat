package org.openchat.domain.post;

import org.openchat.infrastructure.persistence.IdGenerator;

import java.time.LocalDateTime;

public class PostBuilder {

    private String postId = new IdGenerator().nextId();
    private String userId = "1234";
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
        Post post = new Post(postId, userId, text, dateTime);
        return post;
    }
}
