package org.openchat.core.actions;

import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostRepository;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class RetrieveWall {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public RetrieveWall(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Optional<List<Post>> execute(String userId) {
        if (!userRepository.userFor(userId).isPresent()) {
            return Optional.empty();
        }
        List<User> followees = userRepository.followeesFor(userId);
        List<Post> posts = postRepository.postsInReverseChronologicalOrderFor(userIds(userId, followees));
        return Optional.of(posts);
    }

    private List<String> userIds(String userId, List<User> followees) {
        List<String> ids = followees.stream().map(User::id).collect(toList());
        ids.add(0, userId);
        return ids;
    }
}
