package org.openchat.core.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostRepositoryInMemory;
import org.openchat.core.domain.user.User;
import org.openchat.core.domain.user.UserRepositoryInMemory;
import org.openchat.core.infrastructure.Clock;
import org.openchat.core.infrastructure.IDGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.PostBuilder.aPost;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class CreatePostShould {

    private static final String USER_ID = "2322434";
    private static final String POST_TEXT = "Some text";
    private static final User USER = aUser().withId(USER_ID).build();
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2017, 12, 30, 10, 30, 00);

    @Mock
    UserRepositoryInMemory userRepository;
    @Mock IDGenerator idGenerator;
    @Mock
    PostRepositoryInMemory postRepository;
    @Mock Clock clock;

    private CreatePost createPost;

    @Before
    public void initialise() {
        createPost = new CreatePost(clock, idGenerator, userRepository, postRepository);
    }

    @Test public void
    return_nothing_if_user_does_not_exist() {
        given(userRepository.userFor(USER_ID)).willReturn(Optional.empty());

        Optional<Post> result = createPost.execute(USER_ID, POST_TEXT);

        assertThat(result).isNotPresent();
    }

    @Test public void
    store_new_post() {
        given(userRepository.userFor(USER_ID)).willReturn(Optional.of(USER));
        Post newPost = aPost()
                            .withId(idGenerator.nextId())
                            .withUserId(USER_ID)
                            .withText(POST_TEXT)
                            .withDateTime(clock.dateTime())
                            .build();

        Optional<Post> result = createPost.execute(USER_ID, POST_TEXT);

        verify(postRepository).add(newPost);
        assertThat(result).contains(newPost);
    }

}