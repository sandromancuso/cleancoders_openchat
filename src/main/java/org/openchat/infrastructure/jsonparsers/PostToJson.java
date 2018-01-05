package org.openchat.infrastructure.jsonparsers;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;

import java.util.List;

public class PostToJson {

    public static JsonArray jsonFor(List<Post> timeline) {
        JsonArray timelineJson = new JsonArray();
        timeline.forEach(p -> timelineJson.add(jsonFor(p)));
        return timelineJson;
    }

    public static JsonObject jsonFor(Post post) {
        return new JsonObject()
                .add("postId", post.id())
                .add("userId", post.userId())
                .add("text", post.text())
                .add("date", post.dateAsString())
                .add("time", post.timeAsString());
    }

}
