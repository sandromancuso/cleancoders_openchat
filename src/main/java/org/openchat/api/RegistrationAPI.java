package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.RegistrationData;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import org.openchat.domain.user.UsernameAlreadyInUseException;
import org.openchat.infrastructure.jsonparser.UserToJson;
import spark.Request;
import spark.Response;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;

public class RegistrationAPI {
    private static final String JSON = "application/json";
    private UserService userService;

    public RegistrationAPI(UserService userService) {
        this.userService = userService;
    }

    public String register(Request request, Response response) {
        try {
            User user = userService.create(registrationData(request));
            response.status(CREATED_201);
            response.type(JSON);
            return UserToJson.jsonFor(user);
        } catch (UsernameAlreadyInUseException e) {
            response.status(BAD_REQUEST_400);
            return "Username already in use.";
        }
    }

    private RegistrationData registrationData(Request request) {
        JsonObject registrationJson = Json.parse(request.body()).asObject();
        return new RegistrationData(
                            registrationJson.getString("username", ""),
                            registrationJson.getString("password", ""),
                            registrationJson.getString("about", ""));
    }
}
