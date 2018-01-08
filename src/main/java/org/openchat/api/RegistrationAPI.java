package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.RegistrationData;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.Optional;

public class RegistrationAPI {
    private UserService userService;

    public RegistrationAPI(UserService userService) {
        this.userService = userService;
    }

    public String register(Request request, Response response) {
        Optional<User> user = userService.create(registrationData(request));
        return prepareResponse(response, user);
    }

    private String prepareResponse(Response response, Optional<User> user) {
        if (user.isPresent()) {
            response.status(201);
            response.type("application/json");
            return jsonFor(user.get());
        }
        response.status(400);
        return "Username already in use.";
    }

    private String jsonFor(User user) {
        return new JsonObject()
                .add("userId", user.userId())
                .add("username", user.username())
                .add("about", user.about())
                .toString();

    }

    private RegistrationData registrationData(Request request) {
        JsonObject registrationJson = Json.parse(request.body()).asObject();
        return new RegistrationData(
                            registrationJson.getString("username", ""),
                            registrationJson.getString("password", ""),
                            registrationJson.getString("about", ""));
    }
}
