package org.openchat.web.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.core.actions.CreateFollowing;
import org.openchat.core.actions.CreateFollowing.FollowingData;
import org.openchat.core.actions.CreateFollowing.InvalidUserException;
import spark.Request;
import spark.Response;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

public class FollowAPI {
    private CreateFollowing createFollowing;

    public FollowAPI(CreateFollowing createFollowing) {
        this.createFollowing = createFollowing;
    }

    public String follow(Request request, Response response) {
        FollowingData followingData = followingDataFrom(request.body());
        try {
            createFollowing.execute(followingData);
            response.status(CREATED_201);
        } catch (InvalidUserException e) {
            response.status(BAD_REQUEST_400);
        }
        return "";
    }

    private FollowingData followingDataFrom(String followingJsonString) {
        JsonObject followingJson = Json.parse(followingJsonString).asObject();
        return new FollowingData(followingJson.getString("followerId", ""),
                                 followingJson.getString("followeeId", ""));
    }
}
