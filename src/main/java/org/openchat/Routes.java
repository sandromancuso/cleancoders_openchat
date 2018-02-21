package org.openchat;

import org.openchat.api.APIContext;
import org.openchat.api.UserApi;
import org.openchat.usecases.UseCaseContext;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;

public class Routes {

    private UserApi userApi;

    public void create() {
        UseCaseContext.initialize();
        APIContext.initialize();
        userApi = new UserApi();
        openchatRoutes();
    }

    private void openchatRoutes() {
        get("status", (req, res) -> "OpenChat: OK!");
        post("users", (req, res) -> userApi.registerUser(req, res));
    }

}
