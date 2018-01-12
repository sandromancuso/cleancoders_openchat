package org.openchat.api;

import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import org.openchat.domain.user.UserDoesNotExistException;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.jsonparser.PostToJson.toJson;

public class WallAPI {
    private static final String JSON = "application/json";
    private PostService postService;

    public WallAPI(PostService postService) {
        this.postService = postService;
    }

    public String wall(Request request, Response response) {
        String userId = request.params("userId");

        try {
            List<Post> wall = postService.wallFor(userId);
            response.status(OK_200);
            response.type(JSON);
            return toJson(wall);
        } catch (UserDoesNotExistException e) {
            response.status(BAD_REQUEST_400);
            return "User does not exist.";
        }
    }
}
