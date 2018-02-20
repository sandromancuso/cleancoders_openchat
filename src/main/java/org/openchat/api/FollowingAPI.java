package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.users.Following;
import org.openchat.domain.users.FollowingAlreadyExistsException;
import org.openchat.domain.users.User;
import org.openchat.domain.users.UserService;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.*;
import static org.openchat.infrastructure.json.UserJson.jsonFor;

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

    public String getFollowees(Request request, Response response) {
        String followerId = request.params("followerId");
        List<User> followees = userService.followeesFor(followerId);
        response.status(OK_200);
        response.type("application/json");
        return jsonFor(followees);
    }

    private Following followingFrom(Request request) {
        JsonObject jsonObject = Json.parse(request.body()).asObject();
        String followerId = jsonObject.getString("followerId", "");
        String followeeId = jsonObject.getString("followeeId", "");
        return new Following(followerId, followeeId);
    }
}
