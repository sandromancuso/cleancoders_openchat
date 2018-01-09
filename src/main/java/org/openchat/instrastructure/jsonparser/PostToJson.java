package org.openchat.instrastructure.jsonparser;

import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;

public class PostToJson {
    public static String toJson(Post post) {
        return new JsonObject()
                        .add("postId", post.postId())
                        .add("userId", post.userId())
                        .add("text", post.text())
                        .add("date", post.dateAsString())
                        .add("time", post.timeAsString())
                        .toString();
    }
}
