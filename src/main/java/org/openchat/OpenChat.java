package org.openchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static org.eclipse.jetty.http.HttpStatus.NOT_IMPLEMENTED_501;
import static spark.Spark.*;

public class OpenChat {

    private static Logger logger = LoggerFactory.getLogger(OpenChat.class);

    private static final String API_NOT_IMPLEMENTED = "API not implemented.";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error.";

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
            res.status(NOT_IMPLEMENTED_501);
            logger.error(INTERNAL_SERVER_ERROR + ": " + req.pathInfo());
            return INTERNAL_SERVER_ERROR;
        });
    }

    private void configureNotImplemented() {
        notFound((req, res) -> {
            res.status(NOT_IMPLEMENTED_501);
            logger.error(API_NOT_IMPLEMENTED + ": " + req.pathInfo());
            return API_NOT_IMPLEMENTED;
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
