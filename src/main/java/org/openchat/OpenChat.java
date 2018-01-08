package org.openchat;

import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.port;

public class OpenChat {
    public void start() {
        port(4321);
        get("helloworld", (request, response) -> "Hello World!");
    }

    public void stop() {
        Spark.stop();
    }
}
