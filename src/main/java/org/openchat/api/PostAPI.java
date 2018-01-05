package org.openchat.api;

import com.eclipsesource.json.Json;
import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostService;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.openchat.infrastructure.jsonparsers.PostToJson.jsonFor;

public class PostAPI {

    private static final String JSON = "application/json";
    private PostService postService;

    public PostAPI(PostService postService) {
        this.postService = postService;
    }

    public String createPost(Request request, Response response) {
        String userId = request.params("userId");
        String postText = postTextFrom(request.body());
        Optional<Post> post = postService.createPost(userId, postText);
        return prepareCreatePostResponse(response, post);
    }

    private String prepareCreatePostResponse(Response response, Optional<Post> post) {
        if (post.isPresent()) {
            response.status(CREATED_201);
            response.type(JSON);
            return jsonFor(post.get()).toString();
        }
        response.status(BAD_REQUEST_400);
        return "";
    }

    private String postTextFrom(String requestBody) {
        return Json.parse(requestBody).asObject().get("text").asString();
    }
}
