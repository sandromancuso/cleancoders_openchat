package org.openchat.domain.post;

import org.openchat.domain.user.User;

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

    public List<Post> postsInReverseChronologicalOrderFor(List<User> users) {
        return unmodifiableList(
                    posts.stream()
                            .filter(post -> belongsTo(users, post.userId()))
                            .collect(toList()));
    }

    private boolean belongsTo(List<User> users, String userId) {
        return users.stream().anyMatch(user -> user.userId().equals(userId));
    }

}
