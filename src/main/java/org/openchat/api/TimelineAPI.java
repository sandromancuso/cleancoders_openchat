package org.openchat.api;

import org.openchat.domain.post.Post;
import org.openchat.domain.post.PostService;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.jsonparsers.PostToJson.jsonFor;

public class TimelineAPI {

    private static final String JSON = "application/json";

    private PostService postService;

    public TimelineAPI(PostService postService) {
        this.postService = postService;
    }

    public String timeline(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> timeline = postService.timelineFor(userId);
        return prepareTimelineResponse(response, timeline);
    }

    private String prepareTimelineResponse(Response response, List<Post> timeline) {
        response.status(OK_200);
        response.type(JSON);
        return jsonFor(timeline).toString();
    }
}
