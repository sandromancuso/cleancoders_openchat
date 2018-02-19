package org.openchat;

import org.openchat.api.LoginAPI;
import org.openchat.api.PostsAPI;
import org.openchat.api.UsersAPI;
import org.openchat.domain.posts.Clock;
import org.openchat.domain.posts.LanguageService;
import org.openchat.domain.posts.PostRepository;
import org.openchat.domain.posts.PostService;
import org.openchat.domain.users.IdGenerator;
import org.openchat.domain.users.UserRepository;
import org.openchat.domain.users.UserService;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;

public class Routes {

    private UsersAPI usersAPI;
    private LoginAPI loginAPI;
    private PostsAPI postsAPI;

    public void create() {
        createAPIs();
        swaggerRoutes();
        openchatRoutes();
    }

    private void createAPIs() {
        IdGenerator idGenerator = new IdGenerator();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(idGenerator, userRepository);

        Clock clock = new Clock();
        PostRepository postRepository = new PostRepository();
        LanguageService languageService = new LanguageService();
        PostService postService = new PostService(languageService, idGenerator, clock, postRepository);

        usersAPI = new UsersAPI(userService);
        loginAPI = new LoginAPI(userRepository);
        postsAPI = new PostsAPI(postService);
    }

    private void openchatRoutes() {
        get("status", (req, res) -> "OpenChat: OK!");
        post("users", (req, res) -> usersAPI.createUser(req, res));
        post("login", (req, res) -> loginAPI.login(req, res));
        post("users/:userId/timeline", (req, res) -> postsAPI.createPost(req, res));
        get("users/:userId/timeline", (req, res) -> postsAPI.postsByUser(req, res));
    }

    private void swaggerRoutes() {
        options("users", (req, res) -> "OK");
        options("login", (req, res) -> "OK");
        options("users/:userId/timeline", (req, res) -> "OK");
        options("followings", (req, res) -> "OK");
        options("followings/:userId/followees", (req, res) -> "OK");
        options("users/:userId/wall", (req, res) -> "OK");
    }
}
