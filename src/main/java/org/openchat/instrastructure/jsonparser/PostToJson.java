package org.openchat.instrastructure.jsonparser;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;

import java.util.List;

public class PostToJson {

    public static String toJson(List<Post> posts) {
        JsonArray jsonArray = new JsonArray();
        posts.forEach(post -> jsonArray.add(toJsonObject(post)));
        return jsonArray.toString();
    }

    public static String toJson(Post post) {
        return toJsonObject(post)
                        .toString();
    }

    private static JsonObject toJsonObject(Post post) {
        return new JsonObject()
                .add("postId", post.postId())
                .add("userId", post.userId())
                .add("text", post.text())
                .add("date", post.dateAsString())
                .add("time", post.timeAsString());
    }
}
