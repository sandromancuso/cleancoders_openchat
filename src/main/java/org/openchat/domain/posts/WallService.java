package org.openchat.domain.posts;

import org.openchat.domain.users.User;
import org.openchat.domain.users.UserService;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class WallService {
    private final UserService userService;
    private final PostRepository postRepository;

    public WallService(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public List<Post> wallFor(String userId) {
        List<User> followees = userService.followeesFor(userId);
        List<String> userIds = followees.stream()
                                        .map(user -> user.id())
                                        .collect(toList());
        userIds.add(userId);
        List<Post> wallPosts = postRepository.postsBy(userIds);
        return wallPosts;
    }
}
