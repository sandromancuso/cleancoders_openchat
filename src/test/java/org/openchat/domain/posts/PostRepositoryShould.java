package org.openchat.domain.posts;

import org.junit.Before;
import org.junit.Test;

public class PostRepositoryShould {

    private static final Post ALICE_POST_1 = null;
    private PostRepository postRepository;

    @Before
    public void initialise() {
        postRepository = new PostRepository();
    }

    @Test public void
    return_posts_for_a_given_user_in_reverse_chronological_order() {
        postRepository.add(ALICE_POST_1);
//        postRepository.add(CHARLIE_POST_1);
//        postRepository.add(ALICE_POST_2);
//
//        List<Post> result = postRepository.postsBy(ALICE.id());
//
//        assertThat(result).containsExactly(ALICE_POST_2, ALICE_POST_1);
    }

}