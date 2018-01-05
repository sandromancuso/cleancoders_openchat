package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.LoginData;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import org.openchat.infrastructure.jsonparsers.UserToJson;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

public class LoginAPI {
    private static final String JSON = "application/json";
    private static final String INVALID_CREDENTIALS = "Invalid credentials.";
    private UserService userService;

    public LoginAPI(UserService userService) {
        this.userService = userService;
    }

    public String login(Request request, Response response) {
        Optional<User> user = userService.login(loginData(request.body()));
        return prepareResponse(response, user);
    }

    private String prepareResponse(Response response, Optional<User> user) {
        if (user.isPresent()) {
            response.status(OK_200);
            response.type(JSON);
            return UserToJson.jsonFor(user.get()).toString();
        } else {
            response.status(BAD_REQUEST_400);
            return INVALID_CREDENTIALS;
        }
    }

    private LoginData loginData(String loginJson) {
        JsonObject jsonObject = Json.parse(loginJson).asObject();
        return new LoginData(jsonObject.getString("username", ""),
                            jsonObject.getString("password", ""));
    }
}
