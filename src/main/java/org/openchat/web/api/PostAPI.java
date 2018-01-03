package org.openchat.web.api;

import com.eclipsesource.json.Json;
import org.openchat.core.actions.CreatePost;
import org.openchat.core.actions.RetrieveTimeline;
import org.openchat.core.domain.post.Post;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.*;
import static org.openchat.web.infrastructure.jsonparsers.PostToJson.jsonFor;

public class PostAPI {

    private static final String JSON = "application/json";
    private CreatePost createPost;
    private RetrieveTimeline retrieveTimeline;

    public PostAPI(CreatePost createPost, RetrieveTimeline retrieveTimeline) {
        this.createPost = createPost;
        this.retrieveTimeline = retrieveTimeline;
    }

    public String createPost(Request request, Response response) {
        String userId = request.params("userId");
        String postText = postTextFrom(request.body());
        Optional<Post> post = createPost.execute(userId, postText);
        return prepareCreatePostResponse(response, post);
    }

    public String timeline(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> timeline = retrieveTimeline.execute(userId);
        return prepareTimelineResponse(response, timeline);
    }

    private String prepareTimelineResponse(Response response, List<Post> timeline) {
        response.status(OK_200);
        response.type(JSON);
        return jsonFor(timeline).toString();
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
