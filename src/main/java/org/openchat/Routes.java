package org.openchat;

import org.openchat.api.APIContext;
import org.openchat.api.LoginApi;
import org.openchat.api.UserApi;
import org.openchat.usecases.UseCaseContext;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;

public class Routes {

    private UserApi userApi;
    private LoginApi loginAPI;

    public void create() {
        UseCaseContext.initialize();
        APIContext.initialize();
        userApi = new UserApi();
        loginAPI = new LoginApi();
        openchatRoutes();
    }

    private void openchatRoutes() {
        get("status", (req, res) -> "OpenChat: OK!");
        post("users", (req, res) -> userApi.registerUser(req, res));
        post("login", (req, res) -> loginAPI.login(req, res));
    }

}
