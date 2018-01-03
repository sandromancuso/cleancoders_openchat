package org.openchat.core.actions;

import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostRepository;
import org.openchat.core.domain.user.UserRepository;
import org.openchat.core.infrastructure.Clock;
import org.openchat.core.infrastructure.IDGenerator;

import java.util.Optional;

public class CreatePost {
    private final Clock clock;
    private final IDGenerator idGenerator;
    private UserRepository userRepository;
    private final PostRepository postRepository;

    public CreatePost(Clock clock,
                      IDGenerator idGenerator,
                      UserRepository userRepository,
                      PostRepository postRepository) {
        this.clock = clock;
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Optional<Post> execute(String userId, String postText) {
        boolean userIdFound = userRepository.userFor(userId).isPresent();
        if (userIdFound) {
            Post post = createPost(userId, postText);
            return Optional.of(post);
        }
        return Optional.empty();
    }

    private Post createPost(String userId, String postText) {
        Post post = new Post(idGenerator.nextId(), userId, postText, clock.dateTime());
        postRepository.add(post);
        return post;
    }
}
