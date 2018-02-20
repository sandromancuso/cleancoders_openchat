package org.openchat.domain.posts;

import org.junit.Before;
import org.junit.Test;
import org.openchat.domain.users.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.infrastructure.builders.PostBuilder.aPost;
import static org.openchat.infrastructure.builders.UserBuilder.aUser;

public class PostRepositoryShould {


    private static final User ALICE = aUser().build();
    private static final Post ALICE_POST_1 = aPost().withUserId(ALICE.id()).build();
    private static final Post ALICE_POST_2 = aPost().withUserId(ALICE.id()).build();

    private static final User CHARLIE = aUser().build();
    private static final Post CHARLIE_POST_1 = aPost().withUserId(CHARLIE.id()).build();

    private PostRepository postRepository;

    @Before
    public void initialise() {
        postRepository = new PostRepository();
    }

    @Test public void
    return_posts_for_a_given_user_in_reverse_chronological_order() {
        postRepository.add(ALICE_POST_1);
        postRepository.add(CHARLIE_POST_1);
        postRepository.add(ALICE_POST_2);

        List<Post> result = postRepository.postsBy(ALICE.id());

        assertThat(result).containsExactly(ALICE_POST_2, ALICE_POST_1);
    }

}