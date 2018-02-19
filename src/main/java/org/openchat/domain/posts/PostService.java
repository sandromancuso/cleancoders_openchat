package org.openchat.domain.posts;

import org.openchat.domain.users.IdGenerator;

import java.time.LocalDateTime;

public class PostService {
    private LanguageService languageService;
    private final IdGenerator idGenerator;
    private final Clock clock;
    private PostRepository repository;

    public PostService(LanguageService languageService,
                       IdGenerator idGenerator,
                       Clock clock,
                       PostRepository repository) {
        this.languageService = languageService;
        this.idGenerator = idGenerator;
        this.clock = clock;
        this.repository = repository;
    }

    public Post createPost(String userId, String text) throws InappropriateLanguageException{
        validate(text);
        Post post = createNewPost(userId, text);
        repository.add(post);
        return post;
    }

    private Post createNewPost(String userId, String text) {
        String postId = idGenerator.next();
        LocalDateTime now = clock.now();
        return new Post(postId, userId, text, now);
    }

    private void validate(String text) throws InappropriateLanguageException {
        if (languageService.isInappropriate(text)) {
            throw new InappropriateLanguageException();
        }
    }
}
