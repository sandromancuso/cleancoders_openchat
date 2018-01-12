package org.openchat.domain.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.infrastructure.persistence.IdGenerator;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserDoesNotExistException;
import org.openchat.domain.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.domain.post.PostBuilder.aPost;
import static org.openchat.domain.user.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceShould {

    private static final String NON_EXISTENT_USER_ID = "343werq23";
    private static final String POST_TEXT = "some text";
    private static final String POST_ID = "123425";
    private static final String ALICE_ID = "ALSDFLK23";
    private static final String TEXT = "Some text";
    private static final LocalDateTime TODAY = LocalDateTime.now();

    private static final Post POST_1 = aPost().build();
    private static final Post POST_2 = aPost().build();
    private static final Post POST_3 = aPost().build();

    private static final User ALICE = aUser().withUserId(ALICE_ID).build();
    private static final User BOB = aUser().build();
    private static final User CHARLIE = aUser().build();

    @Mock UserService userService;
    @Mock PostRepository postRepository;
    @Mock IdGenerator idGenerator;
    @Mock Clock clock;

    private PostService postService;

    @Before
    public void initialise() {
        postService = new PostService(userService,
                                        postRepository,
                                        idGenerator,
                                        clock);
        given(idGenerator.nextId()).willReturn(POST_ID);
        given(clock.now()).willReturn(TODAY);
        given(userService.userBy(ALICE_ID)).willReturn(Optional.of(ALICE));
    }

    @Test(expected = UserDoesNotExistException.class) public void
    throw_exception_when_creating_a_post_for_a_non_existing_user() {
        given(userService.userBy(NON_EXISTENT_USER_ID)).willReturn(empty());

        postService.createPost(NON_EXISTENT_USER_ID, POST_TEXT);
    }
    
    @Test public void
    store_a_new_post() {
        Post post = aPost()
                        .withPostId(POST_ID)
                        .withUserId(ALICE_ID)
                        .withText(TEXT)
                        .withDateTime(TODAY)
                        .build();

        postService.createPost(ALICE_ID, TEXT);

        verify(postRepository).add(post);
    }
    
    @Test public void
    return_a_timeline_for_a_given_user() {
        given(postRepository.postsInReverseChronologicalOrderFor(ALICE_ID)).willReturn(asList(POST_2, POST_1));

        List<Post> timeline = postService.timelineFor(ALICE_ID);

        assertThat(timeline).containsExactly(POST_2, POST_1);
    }
    
    @Test(expected = UserDoesNotExistException.class) public void
    throw_exception_when_returning_a_timeline_for_a_non_existing_user() {
        given(userService.userBy(NON_EXISTENT_USER_ID)).willReturn(empty());

        postService.timelineFor(NON_EXISTENT_USER_ID);
    }

    @Test public void
    return_the_wall_for_a_given_user() {
        given(userService.followeesFor(ALICE.userId())).willReturn(asList(BOB, CHARLIE));
        given(postRepository.postsInReverseChronologicalOrderFor(asList(BOB, CHARLIE, ALICE)))
                .willReturn(asList(POST_3, POST_2, POST_1));

        List<Post> wall = postService.wallFor(ALICE_ID);

        assertThat(wall).containsExactly(POST_3, POST_2, POST_1);
    }

    @Test(expected = UserDoesNotExistException.class) public void
    throw_exception_when_return_wall_for_a_non_existing_user() {
        given(userService.userBy(NON_EXISTENT_USER_ID)).willReturn(empty());

        postService.wallFor(NON_EXISTENT_USER_ID);
    }
}