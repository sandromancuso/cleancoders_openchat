package org.openchat.web;

import org.openchat.core.actions.*;
import org.openchat.core.domain.post.PostRepository;
import org.openchat.core.domain.user.UserRepository;
import org.openchat.core.infrastructure.Clock;
import org.openchat.core.infrastructure.IDGenerator;
import org.openchat.web.api.*;
import spark.Spark;

import static spark.Spark.*;

public class OpenChat {

    private Clock clock = new Clock();

    private UserRepository userRepository = new UserRepository();
    private PostRepository postRepository = new PostRepository();
    private IDGenerator idGenerator = new IDGenerator();

    private RegisterUser registerUser = new RegisterUser(idGenerator, userRepository);
    private Login login = new Login(userRepository);
    private CreatePost createPost = new CreatePost(clock, idGenerator, userRepository, postRepository);
    private RetrieveTimeline retrieveTimeline = new RetrieveTimeline(postRepository);
    private CreateFollowing createFollowing = new CreateFollowing(userRepository);
    private RetrieveWall retrieveWall = new RetrieveWall(userRepository, postRepository);
    private RetrieveAllUsers retrieveAllUsers = new RetrieveAllUsers(userRepository);

    private RegistrationAPI registrationAPI = new RegistrationAPI(registerUser);
    private LoginAPI loginAPI = new LoginAPI(login);
    private PostAPI postAPI = new PostAPI(createPost, retrieveTimeline);
    private FollowAPI followAPI = new FollowAPI(createFollowing);
    private WallAPI wallAPI = new WallAPI(retrieveWall);
    private UserAPI userAPI = new UserAPI(retrieveAllUsers);

    public void start() {
        port(4321);
        get("helloworld", (req, res) -> "Hello World!");
        post("registration", registrationAPI::registerUser);
        post("login", loginAPI::login);
        post("user/:userId/posts", postAPI::createPost);
        get("user/:userId/timeline", postAPI::timeline);
        get("user/:userId/wall", wallAPI::wall);
        post("follow", followAPI::follow);
        get("users", userAPI::allUsers);
    }

    public void stop() {
        Spark.stop();
    }
}
