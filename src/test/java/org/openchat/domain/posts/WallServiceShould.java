package org.openchat.domain.posts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.users.User;
import org.openchat.domain.users.UserService;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.openchat.infrastructure.builders.PostBuilder.aPost;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class WallServiceShould {

    private static final User ALICE = aUser().build();
    private static final User CHARLIE = aUser().build();
    private static final User LUCY = aUser().build();

    private static final List<Post> WALL_POSTS = asList(aPost().build());

    @Mock UserService userService;
    @Mock PostRepository postRepository;

    private WallService wallService;

    @Before
    public void initialise() {
        wallService = new WallService(userService, postRepository);
    }

    @Test public void
    return_wall_for_a_given_user() {
        given(userService.followeesFor(ALICE.id())).willReturn(asList(CHARLIE, LUCY));
        given(postRepository.postsBy(asList(CHARLIE.id(), LUCY.id(), ALICE.id()))).willReturn(WALL_POSTS);

        List<Post> result = wallService.wallFor(ALICE.id());

        assertThat(result).isEqualTo(WALL_POSTS);
    }

}