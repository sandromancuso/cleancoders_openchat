package org.openchat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;

public class OpenChat {

    private static Logger logger = LoggerFactory.getLogger(OpenChat.class);

    public void start() {
        port(4321);
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        setLog();
        createRoutes();
    }

    private void createRoutes() {
        get("hello", (req, res) -> "Hello OpenChat!");
    }

    public void awaitInitialization() {
        Spark.awaitInitialization();
    }

    private void setLog() {
        before((request, response) -> {
            logger.info("URL request: " + request.requestMethod() + " " + request.uri() + " - headers: " + request.headers());
        });
    }

    public void stop() {
        Spark.stop();
    }

}
