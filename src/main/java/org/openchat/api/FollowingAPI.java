package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.users.Following;
import org.openchat.domain.users.FollowingAlreadyExistsException;
import org.openchat.domain.users.UserService;
import spark.Request;
import spark.Response;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

public class FollowingAPI {
    private UserService userService;

    public FollowingAPI(UserService userService) {
        this.userService = userService;
    }

    public String createFollowing(Request request, Response response) {
        Following following = followingFrom(request);
        try {
            userService.addFollowing(following);
            response.status(CREATED_201);
            return "";
        } catch (FollowingAlreadyExistsException e) {
            response.status(BAD_REQUEST_400);
            return "Following already exists.";
        }
    }

    private Following followingFrom(Request request) {
        JsonObject jsonObject = Json.parse(request.body()).asObject();
        String followerId = jsonObject.getString("followerId", "");
        String followeeId = jsonObject.getString("followeeId", "");
        return new Following(followerId, followeeId);
    }
}
