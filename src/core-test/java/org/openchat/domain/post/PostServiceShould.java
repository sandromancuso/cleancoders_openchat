package org.openchat.domain.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserService;
import org.openchat.infrastructure.Clock;
import org.openchat.infrastructure.IDGenerator;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.PostBuilder.aPost;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceShould {

    private static final User ALICE = aUser().build();
    private static final User BOB = aUser().build();
    private static final User CHARLIE = aUser().build();
    private static final User UNKNOWN_USER = aUser().build();

    private static final String USER_ID = "2322434";
    private static final String POST_TEXT = "Some text";
    private static final User USER = aUser().withId(USER_ID).build();


    private static final Post POST_1 = aPost().withId("1").build();
    private static final Post POST_2 = aPost().withId("2").build();

    @Mock
    UserService userService;
    @Mock
    IDGenerator idGenerator;
    @Mock
    PostRepository postRepository;
    @Mock
    Clock clock;

    private PostService postService;

    @Before
    public void initialise() {
        postService = new PostService(clock, idGenerator, userService, postRepository);
    }

    @Test
    public void
    return_nothing_if_user_does_not_exist() {
        given(userService.userFor(USER_ID)).willReturn(Optional.empty());

        Optional<Post> result = postService.createPost(USER_ID, POST_TEXT);

        assertThat(result).isNotPresent();
    }

    @Test public void
    store_new_post() {
        given(userService.userFor(USER_ID)).willReturn(Optional.of(USER));
        Post newPost = aPost()
                .withId(idGenerator.nextId())
                .withUserId(USER_ID)
                .withText(POST_TEXT)
                .withDateTime(clock.dateTime())
                .build();

        Optional<Post> result = postService.createPost(USER_ID, POST_TEXT);

        verify(postRepository).add(newPost);
        assertThat(result).contains(newPost);
    }

    @Test public void
    return_posts_belonging_to_the_specified_user() {
        List<Post> posts = asList(POST_2, POST_1);
        given(postRepository.postsInReverseChronologicalOrderFor(USER_ID)).willReturn(posts);

        List<Post> result = postService.timelineFor(USER_ID);

        assertThat(result).isEqualTo(posts);
    }


    @Test public void
    return_no_wall_posts_when_user_does_not_exist() {
        given(userService.userFor(UNKNOWN_USER.id())).willReturn(empty());

        Optional<List<Post>> result = postService.wallFor(UNKNOWN_USER.id());

        assertThat(result).isEmpty();
    }

    @Test public void
    return_wall_containing_users_post_in_reverse_chronological_order_when_she_does_not_follow_anyone() {
        given(userService.userFor(ALICE.id())).willReturn(Optional.of(ALICE));
        given(userService.followeesFor(ALICE.id())).willReturn(EMPTY_LIST);
        given(postRepository.postsInReverseChronologicalOrderFor(asList(ALICE.id()))).willReturn(asList(POST_2, POST_1));

        Optional<List<Post>> wall = postService.wallFor(ALICE.id());

        assertThat(wall.get()).containsExactly(POST_2, POST_1);
    }

    @Test public void
    return_wall_containing_users_and_followees_posts_in_reverse_chronological_order() {
        given(userService.userFor(ALICE.id())).willReturn(Optional.of(ALICE));
        given(userService.followeesFor(ALICE.id())).willReturn(asList(BOB, CHARLIE));
        given(postRepository.postsInReverseChronologicalOrderFor(asList(ALICE.id(), BOB.id(), CHARLIE.id()))).willReturn(asList(POST_2, POST_1));

        Optional<List<Post>> wall = postService.wallFor(ALICE.id());

        assertThat(wall.get()).containsExactly(POST_2, POST_1);
    }

}