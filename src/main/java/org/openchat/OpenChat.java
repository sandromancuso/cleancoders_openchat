package org.openchat;

import org.openchat.api.LoginAPI;
import org.openchat.api.RegistrationAPI;
import org.openchat.domain.user.IdGenerator;
import org.openchat.domain.user.UserRepository;
import org.openchat.domain.user.UserService;
import spark.Spark;

import static spark.Spark.port;
import static spark.Spark.post;

public class OpenChat {

    private RegistrationAPI registrationAPI;
    private LoginAPI loginAPI;

    public OpenChat() {
        initialiseAPIs();
    }

    public void start() {
        port(4321);
        post("registration", registrationAPI::register);
        post("login", loginAPI::login);
    }

    public void stop() {
        Spark.stop();
    }

    private void initialiseAPIs() {
        IdGenerator idGenerator = new IdGenerator();
        UserRepository userRepository = userRepository();
        UserService userService = new UserService(idGenerator, userRepository);

        registrationAPI =  new RegistrationAPI(userService);
        loginAPI = new LoginAPI(userService);
    }

    protected UserRepository userRepository() {
        return new UserRepository();
    }
}
