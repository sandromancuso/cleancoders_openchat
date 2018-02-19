package org.openchat.infrastructure.json;

import com.eclipsesource.json.JsonObject;
import org.openchat.domain.posts.Post;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public class PostJson {
    private static DateTimeFormatter formatter = ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static String toJson(Post post) {
        return new JsonObject()
                        .add("postId", post.postId())
                        .add("userId", post.userId())
                        .add("text", post.text())
                        .add("dateTime", formatter.format(post.dateTime()))
                        .toString();
    }
}
