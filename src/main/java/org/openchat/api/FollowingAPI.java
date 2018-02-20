package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.users.UserService;
import spark.Request;
import spark.Response;

public class FollowingAPI {
    private UserService userService;

    public FollowingAPI(UserService userService) {
        this.userService = userService;
    }

    public String createFollowing(Request request, Response response) {
        JsonObject jsonObject = Json.parse(request.body()).asObject();
        String follower = (String) jsonObject;
        Following following = new Following()
    }
}
