package integration.dsl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class PostDSL {

    public static class ITPost {

        private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        private final String postId;
        private final String userId;
        private final String text;
        private final LocalDateTime dateTime;

        ITPost(String postId, String userId, String text, LocalDateTime dateTime) {
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

        @Override
        public String toString() {
            return reflectionToString(this, MULTI_LINE_STYLE);
        }
    }

    public static class ITPostBuilder {

        private String postId = UUID.randomUUID().toString();
        private String userId = "1234";
        private String text = "some text";
        private LocalDateTime dateTime = LocalDateTime.now();

        public static ITPostBuilder aPost() {
            return new ITPostBuilder();
        }

        public ITPostBuilder withPostId(String postId) {
            this.postId = postId;
            return this;
        }

        public ITPostBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public ITPostBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public ITPostBuilder withDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ITPost build() {
            return new ITPost(postId, userId, text, dateTime);
        }
    }
}
