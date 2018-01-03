package org.openchat.web.api;

import org.openchat.core.actions.RetrieveWall;
import org.openchat.core.domain.post.Post;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.web.infrastructure.jsonparsers.PostToJson.jsonFor;

public class WallAPI {
    private static final String JSON = "application/json";
    
    private RetrieveWall retrieveWall;

    public WallAPI(RetrieveWall retrieveWall) {
        this.retrieveWall = retrieveWall;
    }

    public String wall(Request request, Response response) {
        String userId = request.params("userId");
        Optional<List<Post>> posts = retrieveWall.execute(userId);
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
