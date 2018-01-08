package org.openchat;

import org.openchat.api.RegistrationAPI;
import org.openchat.domain.user.UserService;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class OpenChat {

    private RegistrationAPI registrationAPI;

    public OpenChat() {
        initialiseAPIs();
    }

    public void start() {
        port(4321);
        get("helloworld", (request, response) -> "Hello World!");
        post("registration", registrationAPI::register);
    }

    public void stop() {
        Spark.stop();
    }

    private void initialiseAPIs() {
        registrationAPI =  new RegistrationAPI(new UserService());
    }
}
