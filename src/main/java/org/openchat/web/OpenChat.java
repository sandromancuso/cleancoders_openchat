package org.openchat.web;

import org.openchat.core.actions.*;
import org.openchat.core.domain.post.PostRepository;
import org.openchat.core.domain.post.PostRepositoryInMemory;
import org.openchat.core.domain.user.UserRepository;
import org.openchat.core.domain.user.UserRepositoryInMemory;
import org.openchat.core.domain.user.UserService;
import org.openchat.core.infrastructure.Clock;
import org.openchat.core.infrastructure.IDGenerator;
import org.openchat.web.api.*;
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

        //Actions
        RegisterUser registerUser = new RegisterUser(idGenerator, userRepository);
        Login login = new Login(userRepository);
        CreatePost createPost = new CreatePost(clock, idGenerator, userRepository, postRepository);
        RetrieveTimeline retrieveTimeline = new RetrieveTimeline(postRepository);
        RetrieveWall retrieveWall = new RetrieveWall(userRepository, postRepository);
        RetrieveAllUsers retrieveAllUsers = new RetrieveAllUsers(userRepository);

        // APIs
        registrationAPI = new RegistrationAPI(registerUser);
        loginAPI = new LoginAPI(login);
        postAPI = new PostAPI(createPost);
        timelineAPI = new TimelineAPI(retrieveTimeline);
        followAPI = new FollowAPI(userService);
        wallAPI = new WallAPI(retrieveWall);
        userAPI = new UserAPI(retrieveAllUsers);
    }
}
