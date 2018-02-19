package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.domain.users.User;
import org.openchat.domain.users.UserCredentials;
import org.openchat.domain.users.UserRepository;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.json.UserJson.jsonFor;

public class LoginAPI {

    private UserRepository userRepository;

    public LoginAPI(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(Request request, Response response) {
        UserCredentials credentials = credentialsFrom(request);
        Optional<User> user = userRepository.userFor(credentials);
        return user.isPresent()
                        ? prepareOKResponse(response, user)
                        : prepareErrorResponse(response);
    }

    private String prepareErrorResponse(Response response) {
        response.status(NOT_FOUND_404);
        return "Invalid credentials.";
    }

    private String prepareOKResponse(Response response, Optional<User> user) {
        response.status(OK_200);
        response.type("application/json");
        return jsonFor(user.get());
    }

    private UserCredentials credentialsFrom(Request request) {
        JsonObject json = Json.parse(request.body()).asObject();
        return new UserCredentials(
                            json.getString("username", ""),
                            json.getString("password", ""));
    }
}
