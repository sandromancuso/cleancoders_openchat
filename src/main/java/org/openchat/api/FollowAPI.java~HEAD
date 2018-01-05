package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.UserDoesNotExistException;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

public class FollowAPI {

    private UserService userService;

    public FollowAPI(UserService userService) {
        this.userService = userService;
    }

    public String follow(Request request, Response response) {
        JsonObject requestJson = Json.parse(request.body()).asObject();
        String followerId = requestJson.getString("followerId", "");
        String followeeId = requestJson.getString("followeeId", "");

        try {
            userService.createFollowing(followerId, followeeId);
            response.status(201);
            return "";
        } catch (UserDoesNotExistException e) {
            response.status(400);
            return "At least one of the users does not exist.";
        }

    }
}
