package org.openchat.domain.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.user.IdGenerator;
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
    private static final String USER_ID = "ALSDFLK23";
    private static final String TEXT = "Some text";
    private static final LocalDateTime TODAY = LocalDateTime.now();
    private static final User USER = aUser().withUserId(USER_ID).build();

    private static final Post POST_1 = aPost().build();
    private static final Post POST_2 = aPost().build();

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
        given(userService.userForId(USER_ID)).willReturn(Optional.of(USER));
    }

    @Test(expected = UserDoesNotExistException.class) public void
    throw_exception_when_creating_a_post_for_a_non_existing_user() {
        given(userService.userForId(NON_EXISTENT_USER_ID)).willReturn(empty());

        postService.createPost(NON_EXISTENT_USER_ID, POST_TEXT);
    }
    
    @Test public void
    store_a_new_post() {
        Post post = aPost()
                        .withPostId(POST_ID)
                        .withUserId(USER_ID)
                        .withText(TEXT)
                        .withDateTime(TODAY)
                        .build();

        postService.createPost(USER_ID, TEXT);

        verify(postRepository).add(post);
    }
    
    @Test public void
    return_a_timeline_for_a_given_user() {
        given(postRepository.postsInReverseChronologicalOrderFor(USER_ID)).willReturn(asList(POST_2, POST_1));

        List<Post> timeline = postService.timelineFor(USER_ID);

        assertThat(timeline).containsExactly(POST_2, POST_1);
    }
    
    @Test(expected = UserDoesNotExistException.class) public void
    throw_exception_when_returning_a_timeline_for_a_non_existing_user() {
        given(userService.userForId(NON_EXISTENT_USER_ID)).willReturn(empty());

        postService.timelineFor(NON_EXISTENT_USER_ID);
    }
    
}