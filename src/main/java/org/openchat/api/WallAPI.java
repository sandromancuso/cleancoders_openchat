package org.openchat.api;

import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.jsonparsers.PostToJson.jsonFor;

public class WallAPI {
    private static final String JSON = "application/json";
    
    private PostService postService;

    public WallAPI(PostService postService) {
        this.postService = postService;
    }

    public String wall(Request request, Response response) {
        String userId = request.params("userId");
        Optional<List<Post>> posts = postService.wallFor(userId);
        return prepareTimelineResponse(response, posts);
    }

    private String prepareTimelineResponse(Response response, Optional<List<Post>> posts) {
        if (posts.isPresent()) {
            response.status(OK_200);
            response.type(JSON);
            return jsonFor(posts.get()).toString();
        }
        response.status(BAD_REQUEST_400);
        return "";
    }

}
