package org.openchat.web.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.core.actions.RegisterUser;
import org.openchat.core.actions.RegisterUser.RegistrationData;
import org.openchat.core.domain.user.User;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.CREATED_201;
import static org.openchat.web.infrastructure.jsonparsers.UserToJson.jsonFor;

public class RegistrationAPI {

    private static final String JSON = "application/json";
    private static final String USERNAME_ALREADY_IN_USER = "Username already in use.";
    private RegisterUser registerUser;

    public RegistrationAPI(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    public String registerUser(Request request, Response response) {
        Optional<User> user = registerUser.execute(registrationData(request.body()));
        return prepareResponse(response, user);
    }

    private String prepareResponse(Response response, Optional<User> user) {
        if (user.isPresent()) {
            response.type(JSON);
            response.status(CREATED_201);
            return jsonFor(user.get()).toString();
        } else {
            response.status(BAD_REQUEST_400);
            return USERNAME_ALREADY_IN_USER;
        }
    }

    private RegistrationData registrationData(String registrationJson) {
        JsonObject jsonObject = Json.parse(registrationJson).asObject();
        return new RegistrationData(jsonObject.getString("username", ""),
                                    jsonObject.getString("password", ""),
                                    jsonObject.getString("about", ""));
    }

}
