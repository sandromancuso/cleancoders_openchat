package org.openchat.web.api;

import org.openchat.core.actions.RetrieveTimeline;
import org.openchat.core.domain.post.Post;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.web.infrastructure.jsonparsers.PostToJson.jsonFor;

public class TimelineAPI {

    private static final String JSON = "application/json";

    private RetrieveTimeline retrieveTimeline;

    public TimelineAPI(RetrieveTimeline retrieveTimeline) {
        this.retrieveTimeline = retrieveTimeline;
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



}
