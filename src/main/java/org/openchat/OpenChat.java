package org.openchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;

public class OpenChat {

    private static Logger logger = LoggerFactory.getLogger(OpenChat.class);

    private Routes routes = new Routes();

    public void start() {
        port(4321);
        enableCORS();
        setLog();
        routes.create();
        configureInternalServerError();
        configureNotImplemented();
    }

    public void stop() {
        Spark.stop();
    }

    public void awaitInitialization() {
        Spark.awaitInitialization();
    }

    private void configureInternalServerError() {
        internalServerError((req, res) -> {
            res.type("application/json");
            res.status(501);
            return "Internal server error";
        });
    }

    private void configureNotImplemented() {
        notFound((req, res) -> {
            res.status(501);
            return "API not implemented.";
        });
    }

    private void enableCORS() {
        // Enable Cross Origin Resource Sharing.
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
        });
    }

    private void setLog() {
        before((request, response) -> {
            logger.info("URL request: " + request.requestMethod() + " " + request.uri() + " - headers: " + request.headers());
        });
    }

}
