package org.openchat.core.domain.post;

import java.util.List;

public interface PostRepository {
    void add(Post post);

    List<Post> postsInReverseChronologicalOrderFor(String userId);

    List<Post> postsInReverseChronologicalOrderFor(List<String> userIds);
}
