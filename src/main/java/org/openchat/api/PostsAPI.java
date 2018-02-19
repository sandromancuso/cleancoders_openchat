package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.posts.PostService;
import spark.Request;
import spark.Response;

public class PostsAPI {
    private PostService postService;

    public PostsAPI(PostService postService) {
        this.postService = postService;
    }

    public String createPost(Request request, Response response) {
        String userId = request.params("userId");
        String text = postTextFrom(request);
        postService.createPost(userId, text);
        return "";
    }

    private String postTextFrom(Request request) {
        JsonObject json = Json.parse(request.body()).asObject();
        return json.getString("text", "");
    }
}
