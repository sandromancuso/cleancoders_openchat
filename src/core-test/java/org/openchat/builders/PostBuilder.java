package org.openchat.builders;

import org.openchat.core.domain.post.Post;
import org.openchat.core.infrastructure.IDGenerator;

import java.time.LocalDateTime;

public class PostBuilder {

    private IDGenerator idGenerator = new IDGenerator();

    private String postId = idGenerator.nextId();
    private String userId = idGenerator.nextId();
    private String text = "Some text";
    private LocalDateTime dateTime = LocalDateTime.now();


    public static PostBuilder aPost() {
        return new PostBuilder();
    }

    public PostBuilder withId(String id) {
        this.postId = id;
        return this;
    }

    public PostBuilder withUserId(String id) {
        this.userId = id;
        return this;
    }

    public PostBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public PostBuilder withDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime;
        return this;
    }

    public Post build() {
        return new Post(postId, userId, text, dateTime);
    }
}
