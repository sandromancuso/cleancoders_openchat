package org.openchat.domain.post;

import org.openchat.infrastructure.persistence.IdGenerator;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserDoesNotExistException;
import org.openchat.domain.user.UserService;

import java.util.ArrayList;
import java.util.List;

public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final IdGenerator idGenerator;
    private final Clock clock;

    public PostService(UserService userService,
                       PostRepository postRepository,
                       IdGenerator idGenerator,
                       Clock clock) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.idGenerator = idGenerator;
        this.clock = clock;
    }

    public Post createPost(String userId, String postText) {
        validate(userId);
        Post post = new Post(idGenerator.nextId(), userId, postText, clock.now());
        postRepository.add(post);
        return post;
    }

    public List<Post> timelineFor(String userId) {
        validate(userId);
        return postRepository.postsInReverseChronologicalOrderFor(userId);
    }

    private void validate(String userId) {
        if (!userService.userBy(userId).isPresent())
            throw new UserDoesNotExistException();
    }

    public List<Post> wallFor(String userId) {
        validate(userId);
        User user = userService.userBy(userId).get();
        List<User> followees = userService.followeesFor(userId);
        List<User> allUsers = new ArrayList<>(followees);
        allUsers.add(user);
        return postRepository.postsInReverseChronologicalOrderFor(allUsers);
    }
}
