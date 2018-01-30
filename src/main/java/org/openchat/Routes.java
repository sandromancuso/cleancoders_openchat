package org.openchat;

import static spark.Spark.get;
import static spark.Spark.options;

public class Routes {

    public void create() {
        swaggerRoutes();
        openchatRoutes();
    }

    private void openchatRoutes() {
        get("status", (req, res) -> "OpenChat: OK!");
    }

    private void swaggerRoutes() {
        options("registration", (req, res) -> "OK");
        options("login", (req, res) -> "OK");
        options("user/{userId}/posts", (req, res) -> "OK");
        options("follow", (req, res) -> "OK");
        options("user/{userId}/wall", (req, res) -> "OK");
        options("users", (req, res) -> "OK");
        options("user/{userId}/followees", (req, res) -> "OK");
    }
}
