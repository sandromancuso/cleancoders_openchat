package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.openchat.instrastructure.jsonparser.UserToJson.jsonFor;

public class LoginAPI {

    private UserService userService;

    public LoginAPI(UserService userService) {
        this.userService = userService;
    }

    public String login(Request request, Response response) {
        JsonObject requestJson = Json.parse(request.body()).asObject();
        String username = requestJson.getString("username", "");
        String password = requestJson.getString("password", "");

        Optional<User> user = userService.userBy(username, password);

        return createResponse(response, user);
    }

    private String createResponse(Response response, Optional<User> user) {
        if (user.isPresent()) {
            response.status(200);
            response.type("application/json");
            return jsonFor(user.get());
        }
        response.status(400);
        return "Invalid credentials.";
    }
}
