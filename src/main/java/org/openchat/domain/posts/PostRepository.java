package org.openchat.domain.posts;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PostRepository {

    private List<Post> posts = new LinkedList<>();

    public void add(Post post) {
        posts.add(0, post);
    }

    public List<Post> postsBy(String userId) {
        return posts.stream()
                    .filter(post -> post.userId().equals(userId))
                    .collect(toList());
    }
}
