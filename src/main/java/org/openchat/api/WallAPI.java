package org.openchat.api;

import org.openchat.domain.posts.Post;
import org.openchat.domain.posts.WallService;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.json.PostJson.toJson;

public class WallAPI {
    private WallService wallService;

    public WallAPI(WallService wallService) {
        this.wallService = wallService;
    }

    public String wallByUser(Request request, Response response) {
        String userId = request.params("userId");
        List<Post> wallPosts = wallService.wallFor(userId);
        response.status(OK_200);
        response.type("application/json");
        return toJson(wallPosts);
    }
}
