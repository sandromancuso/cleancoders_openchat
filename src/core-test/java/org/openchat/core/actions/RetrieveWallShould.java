package org.openchat.core.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostRepository;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.openchat.builders.PostBuilder.aPost;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveWallShould {

    private static final User ALICE = aUser().build();
    private static final User BOB = aUser().build();
    private static final User CHARLIE = aUser().build();
    private static final User UNKNOWN_USER = aUser().build();
    private static final Post POST_1 = aPost().build();
    private static final Post POST_2 = aPost().build();

    @Mock UserRepository userRepository;
    @Mock PostRepository postRepository;

    private RetrieveWall retrieveWall;

    @Before
    public void initialise() {
        retrieveWall = new RetrieveWall(userRepository, postRepository);
    }

    @Test public void
    return_empty_if_user_does_not_exist() {
        given(userRepository.userFor(UNKNOWN_USER.id())).willReturn(empty());

        Optional<List<Post>> result = retrieveWall.execute(UNKNOWN_USER.id());

        assertThat(result).isEmpty();
    }

    @Test public void
    return_wall_containing_users_post_in_reverse_chronological_order_when_she_does_not_follow_anyone() {
        given(userRepository.userFor(ALICE.id())).willReturn(Optional.of(ALICE));
        given(userRepository.followeesFor(ALICE.id())).willReturn(EMPTY_LIST);
        given(postRepository.postsInReverseChronologicalOrderFor(asList(ALICE.id()))).willReturn(asList(POST_2, POST_1));

        Optional<List<Post>> wall = retrieveWall.execute(ALICE.id());

        assertThat(wall.get()).containsExactly(POST_2, POST_1);
    }

    @Test public void
    return_wall_containing_users_and_followees_posts_in_reverse_chronological_order() {
        given(userRepository.userFor(ALICE.id())).willReturn(Optional.of(ALICE));
        given(userRepository.followeesFor(ALICE.id())).willReturn(asList(BOB, CHARLIE));
        given(postRepository.postsInReverseChronologicalOrderFor(asList(ALICE.id(), BOB.id(), CHARLIE.id()))).willReturn(asList(POST_2, POST_1));

        Optional<List<Post>> wall = retrieveWall.execute(ALICE.id());

        assertThat(wall.get()).containsExactly(POST_2, POST_1);
    }

}