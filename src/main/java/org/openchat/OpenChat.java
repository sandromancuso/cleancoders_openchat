package org.openchat;

import org.openchat.api.*;
import org.openchat.domain.post.PostRepository;
import org.openchat.domain.post.PostRepositoryInMemory;
import org.openchat.domain.post.PostService;
import org.openchat.domain.user.UserRepository;
import org.openchat.domain.user.UserRepositoryInMemory;
import org.openchat.domain.user.UserService;
import org.openchat.domain.post.Clock;
import org.openchat.infrastructure.db.IDGenerator;
import spark.Spark;

import static spark.Spark.*;

public class OpenChat {

    private RegistrationAPI registrationAPI;
    private LoginAPI loginAPI;
    private PostAPI postAPI;
    private TimelineAPI timelineAPI;
    private FollowAPI followAPI;
    private WallAPI wallAPI;
    private UserAPI userAPI;

    public OpenChat() {
        initialiseDependencies();
    }

    public void start() {
        port(4321);
        createRoutes();
    }

    public void stop() {
        Spark.stop();
    }

    private void createRoutes() {
        get ("helloworld",            (req, res) -> "Hello World!");

        post("registration",          registrationAPI::registerUser);
        post("login",                 loginAPI::login);
        post("user/:userId/posts",    postAPI::createPost);
        get ("user/:userId/timeline", timelineAPI::timeline);
        get ("user/:userId/wall",     wallAPI::wall);
        post("follow",                followAPI::follow);
        get ("users",                 userAPI::allUsers);
    }

    private void initialiseDependencies() {
        // Infrastructure
        Clock clock = new Clock();
        IDGenerator idGenerator = new IDGenerator();

        // Domain
        UserRepository userRepository = new UserRepositoryInMemory();
        PostRepository postRepository = new PostRepositoryInMemory();
        UserService userService = new UserService(idGenerator, userRepository);
        PostService postService = new PostService(clock, idGenerator, userService, postRepository);

        // APIs
        registrationAPI = new RegistrationAPI(userService);
        loginAPI = new LoginAPI(userService);
        postAPI = new PostAPI(postService);
        timelineAPI = new TimelineAPI(postService);
        followAPI = new FollowAPI(userService);
        wallAPI = new WallAPI(postService);
        userAPI = new UserAPI(userService);
    }
}
