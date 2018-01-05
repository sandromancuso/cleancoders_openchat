package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.core.domain.user.Following;
import org.openchat.core.domain.user.InvalidUserException;
import org.openchat.core.domain.user.UserService;
import spark.Request;
import spark.Response;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

public class FollowAPI {
    private UserService userService;

    public FollowAPI(UserService userService) {
        this.userService = userService;
    }

    public String follow(Request request, Response response) {
        Following following = followingFrom(request.body());
        try {
            userService.create(following);
            response.status(CREATED_201);
        } catch (InvalidUserException e) {
            response.status(BAD_REQUEST_400);
        }
        return "";
    }

    private Following followingFrom(String followingJsonString) {
        JsonObject followingJson = Json.parse(followingJsonString).asObject();
        return new Following(followingJson.getString("followerId", ""),
                                 followingJson.getString("followeeId", ""));
    }
}
