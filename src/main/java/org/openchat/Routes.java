package org.openchat;

import org.openchat.api.UserApi;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;

public class Routes {

    private UserApi userApi;

    public void create() {
        userApi = new UserApi();
        openchatRoutes();
    }

    private void openchatRoutes() {
        get("status", (req, res) -> "OpenChat: OK!");
        post("users", (req, res) -> userApi.registerUser(req, res));
    }

}
