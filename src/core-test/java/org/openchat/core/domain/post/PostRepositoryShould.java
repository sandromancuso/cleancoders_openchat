package org.openchat.core.domain.post;

import org.junit.Before;
import org.junit.Test;
import org.openchat.core.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.builders.PostBuilder.aPost;
import static org.openchat.builders.UserBuilder.aUser;

public class PostRepositoryShould {

    private static final LocalDateTime TODAY = LocalDateTime.now();
    private static final LocalDateTime YESTERDAY = TODAY.minusDays(1);
    private static final LocalDateTime DAY_BEFORE_YESTERDAY = TODAY.minusDays(2);

    private static final User ALICE = aUser().withId("123243").build();
    private static final User BOB = aUser().withId("4312353").build();

    private static final Post POST_1 = aPost().withUserId(ALICE.id()).withDateTime(DAY_BEFORE_YESTERDAY).build();
    private static final Post POST_2 = aPost().withUserId(BOB.id()).withDateTime(YESTERDAY).build();
    private static final Post POST_3 = aPost().withUserId(ALICE.id()).withDateTime(TODAY).build();

    private PostRepository postRepository;

    @Before
    public void initialise() {
        postRepository = new PostRepository();
    }

    @Test public void
    return_posts_in_reverse_chronological_order_for_a_given_user() {
        postRepository.add(POST_1);
        postRepository.add(POST_2);
        postRepository.add(POST_3);

        List<Post> result = postRepository.postsInReverseChronologicalOrderFor(ALICE.id());

        assertThat(result).containsExactly(POST_3, POST_1);
    }

    @Test public void
    return_posts_in_reverse_chronological_order_for_multiple_users() {
        postRepository.add(POST_1);
        postRepository.add(POST_2);
        postRepository.add(POST_3);

        List<Post> result = postRepository.postsInReverseChronologicalOrderFor(asList(ALICE.id(), BOB.id()));

        assertThat(result).containsExactly(POST_3, POST_2, POST_1);
    }

}