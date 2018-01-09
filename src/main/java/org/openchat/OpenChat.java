package org.openchat;

import org.openchat.api.LoginAPI;
import org.openchat.api.PostAPI;
import org.openchat.api.RegistrationAPI;
import org.openchat.api.TimelineAPI;
import org.openchat.domain.post.Clock;
import org.openchat.domain.post.PostRepository;
import org.openchat.domain.post.PostService;
import org.openchat.domain.user.IdGenerator;
import org.openchat.domain.user.UserRepository;
import org.openchat.domain.user.UserService;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class OpenChat {

    private RegistrationAPI registrationAPI;
    private LoginAPI loginAPI;
    private PostAPI postAPI;
    private TimelineAPI timelineAPI;

    public OpenChat() {
        initialiseAPIs();
    }

    public void start() {
        port(4321);
        post("registration", registrationAPI::register);
        post("login", loginAPI::login);
        post("user/:userId/posts", postAPI::createPost);
        get("user/:userId/timeline", timelineAPI::timeline);
    }

    public void stop() {
        Spark.stop();
    }

    private void initialiseAPIs() {
        IdGenerator idGenerator = new IdGenerator();

        UserRepository userRepository = userRepository();
        UserService userService = new UserService(idGenerator, userRepository);

        PostRepository postRepository = new PostRepository();
        Clock clock = new Clock();
        PostService postService = new PostService(userService, postRepository, idGenerator, clock);

        registrationAPI =  new RegistrationAPI(userService);
        loginAPI = new LoginAPI(userService);
        postAPI = new PostAPI(postService);
        timelineAPI = new TimelineAPI(postService);
    }

    protected UserRepository userRepository() {
        return new UserRepository();
    }
}
