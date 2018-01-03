package org.openchat.core.domain.post;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class PostRepository {
    private List<Post> posts = new ArrayList<>();

    public void add(Post post) {
        posts.add(post);
    }

    public List<Post> postsInReverseChronologicalOrderFor(String userId) {
        return postsInReverseChronologicalOrderFor(asList(userId));
    }

    public List<Post> postsInReverseChronologicalOrderFor(List<String> userIds) {
        return posts
                    .stream()
                    .filter(post -> userIds.contains(post.userId()))
                    .sorted((p1, p2)-> p1.dateTime().isBefore(p2.dateTime()) ? 1 : -1)
                    .collect(toList());
    }
}
