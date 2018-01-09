package org.openchat.domain.post;

import java.time.LocalDateTime;

public class PostBuilder {

    private String postId = "1232342";
    private String userId = "1234";
    private String text = "some text";
    private LocalDateTime dateTime = LocalDateTime.now();

    public static PostBuilder aPost() {
        return new PostBuilder();
    }

    public PostBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public PostBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public Post build() {
        Post post = new Post(postId, userId, text, dateTime);
        return post;
    }
}
