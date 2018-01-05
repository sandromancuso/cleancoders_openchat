package org.openchat.domain.post;

import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import org.openchat.infrastructure.Clock;
import org.openchat.infrastructure.IDGenerator;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class PostService {

    private final Clock clock;
    private final IDGenerator idGenerator;
    private UserService userService;
    private final PostRepository postRepository;

    public PostService(Clock clock,
                       IDGenerator idGenerator,
                       UserService userService,
                       PostRepository postRepository) {
        this.clock = clock;
        this.idGenerator = idGenerator;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public Optional<Post> createPost(String userId, String postText) {
        boolean userIdFound = userService.userFor(userId).isPresent();
        if (userIdFound) {
            Post post = postFor(userId, postText);
            return Optional.of(post);
        }
        return Optional.empty();
    }

    public List<Post> timelineFor(String userId) {
        return postRepository.postsInReverseChronologicalOrderFor(userId);
    }

    public Optional<List<Post>> wallFor(String userId) {
        if (!userService.userFor(userId).isPresent()) {
            return Optional.empty();
        }
        List<User> followees = userService.followeesFor(userId);
        List<Post> posts = postRepository.postsInReverseChronologicalOrderFor(userIds(userId, followees));
        return Optional.of(posts);
    }

    private List<String> userIds(String userId, List<User> followees) {
        List<String> ids = followees.stream().map(User::id).collect(toList());
        ids.add(0, userId);
        return ids;
    }

    private Post postFor(String userId, String postText) {
        Post post = new Post(idGenerator.nextId(), userId, postText, clock.dateTime());
        postRepository.add(post);
        return post;
    }
}
