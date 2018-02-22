package org.openchat;

import org.openchat.api.*;
import org.openchat.usecases.Subscribe;
import org.openchat.usecases.UseCaseContext;

import static spark.Spark.*;


public class Routes {
    private static boolean TEST_CONFIG = true; // should be environment variable.

    private RegisterUserAPI registerRegisterUserAPI;
    private LoginApi loginAPI;
    private GetUsersAPI getUsersApi;
    private PostDocumentAPI postDocumentApi;
    private GetDocsForUserAPI getDocsForUserApi;
    private SubscribeAPI subscribeApi;

    public void create() {
        UseCaseContext.initialize();
        APIContext.initialize();
        registerRegisterUserAPI = new RegisterUserAPI();
        loginAPI = new LoginApi();
        getUsersApi = new GetUsersAPI();
        postDocumentApi = new PostDocumentAPI();
        getDocsForUserApi = new GetDocsForUserAPI();
        subscribeApi = new SubscribeAPI();
        openchatRoutes();
    }

    private void openchatRoutes() {
        if (TEST_CONFIG)
            delete("repositories", (req, res) -> {APIContext.initialize(); UseCaseContext.initialize(); return "";});
        get("status", (req, res) -> "OpenChat: OK!");
        post("users", (req, res) -> registerRegisterUserAPI.exec(req, res));
        post("login", (req, res) -> loginAPI.exec(req, res));
        get("users", (req, res) -> getUsersApi.exec(req, res));
        post("users/:userId/timeline", (req, res) -> postDocumentApi.exec(req, res));
        get("users/:userId/timeline", (req, res) -> getDocsForUserApi.exec(req, res));
        post("followings", (req, res) -> subscribeApi.exec(req, res));
    }

}
