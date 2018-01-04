package org.openchat.web.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.core.actions.Login;
import org.openchat.core.domain.user.LoginData;
import org.openchat.core.domain.user.User;
import org.openchat.web.infrastructure.jsonparsers.UserToJson;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;

public class LoginAPI {
    private static final String JSON = "application/json";
    private static final String INVALID_CREDENTIALS = "Invalid credentials.";
    private Login login;

    public LoginAPI(Login login) {
        this.login = login;
    }

    public String login(Request request, Response response) {
        Optional<User> user = login.execute(loginData(request.body()));
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
