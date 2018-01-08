package org.openchat;

import org.openchat.api.RegistrationAPI;
import org.openchat.domain.user.IdGenerator;
import org.openchat.domain.user.UserRepository;
import org.openchat.domain.user.UserService;
import spark.Spark;

import static spark.Spark.port;
import static spark.Spark.post;

public class OpenChat {

    private RegistrationAPI registrationAPI;

    public OpenChat() {
        initialiseAPIs();
    }

    public void start() {
        port(4321);
        post("registration", registrationAPI::register);
    }

    public void stop() {
        Spark.stop();
    }

    private void initialiseAPIs() {
        IdGenerator idGenerator = new IdGenerator();
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(idGenerator, userRepository);

        registrationAPI =  new RegistrationAPI(userService);
    }
}
