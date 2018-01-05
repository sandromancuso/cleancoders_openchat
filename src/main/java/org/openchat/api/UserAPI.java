package org.openchat.api;

import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.openchat.infrastructure.jsonparsers.UserToJson.jsonFor;

public class UserAPI {
    private static final String JSON = "application/json";
    private UserService userService;

    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    public String allUsers(Request request, Response response) {
        List<User> users = userService.allUsers();
        response.status(OK_200);
        response.type(JSON);
        return jsonFor(users);
    }

}
