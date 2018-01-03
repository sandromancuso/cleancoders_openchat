package org.openchat.core.actions;

import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostRepository;

import java.util.List;

public class RetrieveTimeline {
    private PostRepository postRepository;

    public RetrieveTimeline(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> execute(String userId) {
        return postRepository.postsInReverseChronologicalOrderFor(userId);
    }
}
