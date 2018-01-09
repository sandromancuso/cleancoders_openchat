package org.openchat.domain.post;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class PostRepository {

    private List<Post> posts = new LinkedList<>();

    public void add(Post post) {
        posts.add(0, post);
    }

    public List<Post> postsInReverseChronologicalOrderFor(String userId) {
        return unmodifiableList(
                    posts.stream()
                            .filter(post -> post.userId().equals(userId))
                            .collect(toList()));
    }
}
