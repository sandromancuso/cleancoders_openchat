package org.openchat;

import org.openchat.api.*;
import org.openchat.usecases.UseCaseContext;

import static spark.Spark.*;


public class Routes {
    private static boolean TEST_CONFIG = true; // should be environment variable.

    private UserApi userApi;
    private LoginApi loginAPI;
    private GetUsersAPI getUsersApi;
    private PostDocumentAPI postDocumentApi;

    public void create() {
        UseCaseContext.initialize();
        APIContext.initialize();
        userApi = new UserApi();
        loginAPI = new LoginApi();
        getUsersApi = new GetUsersAPI();
        postDocumentApi = new PostDocumentAPI();
        openchatRoutes();
    }

    private void openchatRoutes() {
        if (TEST_CONFIG)
            delete("repositories", (req, res) -> {APIContext.initialize(); UseCaseContext.initialize(); return "";});
        get("status", (req, res) -> "OpenChat: OK!");
        post("users", (req, res) -> userApi.registerUser(req, res));
        post("login", (req, res) -> loginAPI.login(req, res));
        get("users", (req, res) -> getUsersApi.getUsers(req, res));
        post("users/:userId/timeline", (req, res) -> postDocumentApi.post(req, res));
    }

}
