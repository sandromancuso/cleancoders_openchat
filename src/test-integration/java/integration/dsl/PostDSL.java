package integration.dsl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class PostDSL {

    public static class Post {

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

    public static class PostBuilder {

        private String postId = UUID.randomUUID().toString();
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
}
