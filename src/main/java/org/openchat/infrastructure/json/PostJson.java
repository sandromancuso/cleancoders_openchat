package org.openchat.infrastructure.json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.posts.Post;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class PostJson {
    private static DateTimeFormatter formatter = ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static String toJson(Post post) {
        return jsonObjectFor(post).toString();
    }

    public static String toJson(List<Post> posts) {
        JsonArray json = new JsonArray();
        posts.forEach(post -> json.add(jsonObjectFor(post)));
        return json.toString();
    }

    private static JsonObject jsonObjectFor(Post post) {
        return new JsonObject()
                .add("postId", post.postId())
                .add("userId", post.userId())
                .add("text", post.text())
                .add("dateTime", formatter.format(post.dateTime()));
    }
}
