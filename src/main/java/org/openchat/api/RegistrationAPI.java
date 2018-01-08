package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.RegistrationData;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

public class RegistrationAPI {
    private UserService userService;

    public RegistrationAPI(UserService userService) {
        this.userService = userService;
    }

    public String register(Request request, Response response) {
        userService.create(registrationData(request));
        return "";
    }

    private RegistrationData registrationData(Request request) {
        JsonObject registrationJson = Json.parse(request.body()).asObject();
        return new RegistrationData(
                            registrationJson.getString("username", ""),
                            registrationJson.getString("password", ""),
                            registrationJson.getString("about", ""));
    }
}
