package org.openchat.api;

import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import org.openchat.domain.user.UserDoesNotExistException;
import org.openchat.infrastructure.jsonparser.PostToJson;
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

    private String postTextFrom(String requestJson) {
        return parse(requestJson).asObject().getString("text", "");
    }
}
