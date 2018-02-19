package org.openchat.domain.posts;

import org.openchat.domain.users.IdGenerator;

import java.time.LocalDateTime;

public class PostService {
    private final IdGenerator idGenerator;
    private final Clock clock;
    private PostRepository repository;

    public PostService(IdGenerator idGenerator, Clock clock, PostRepository repository) {
        this.idGenerator = idGenerator;
        this.clock = clock;
        this.repository = repository;
    }

    public Post createPost(String userId, String text) throws InappropriateLanguageException{
        String postId = idGenerator.next();
        LocalDateTime now = clock.now();
        Post post = new Post(postId, userId, text, now);
        repository.add(post);
        return post;
    }
}
