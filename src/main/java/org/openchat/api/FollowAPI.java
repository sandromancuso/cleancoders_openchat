package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.UserDoesNotExistException;
import org.openchat.domain.user.UserService;
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
        try {
            createFollowing(request);
            response.status(CREATED_201);
            return "";
        } catch (UserDoesNotExistException e) {
            response.status(BAD_REQUEST_400);
            return "At least one of the users does not exist.";
        }
    }

    private void createFollowing(Request request) throws UserDoesNotExistException {
        JsonObject requestJson = Json.parse(request.body()).asObject();
        String followerId = requestJson.getString("followerId", "");
        String followeeId = requestJson.getString("followeeId", "");

        userService.createFollowing(followerId, followeeId);
    }

}
