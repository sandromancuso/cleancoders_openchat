package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import org.openchat.domain.user.UserDoesNotExistException;
import spark.Request;
import spark.Response;

import static com.eclipsesource.json.Json.parse;

public class PostAPI {

    private PostService postService;

    public PostAPI(PostService postService) {
        this.postService = postService;
    }

    public String createPost(Request request, Response response) {
        String userId = request.params("userId");
        String postText = postTextFrom(request.body());

        try {
            Post post = postService.createPost(userId, postText);

            response.status(201);
            response.type("application/json");
            return PostToJson.toJson(post);
        } catch (UserDoesNotExistException e) {
            response.status(400);
            return "User does not exist.";
        }
    }

    public static class PostToJson {
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

    private String postTextFrom(String requestJson) {
        return parse(requestJson).asObject().getString("text", "");
    }
}
