package org.openchat.core.actions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.domain.post.Post;
import org.openchat.core.domain.post.PostRepositoryInMemory;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.openchat.builders.PostBuilder.aPost;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveTimelineShould {

    private static final String USER_ID = "12332";
    private static final Post POST_1 = aPost().withId("1").build();
    private static final Post POST_2 = aPost().withId("2").build();

    @Mock
    PostRepositoryInMemory postRepository;

    private RetrieveTimeline retrieveTimeline;

    @Before
    public void initialise() {
        retrieveTimeline = new RetrieveTimeline(postRepository);
    }

    @Test public void
    return_posts_belonging_to_the_specified_user() {
        List<Post> posts = asList(POST_2, POST_1);
        given(postRepository.postsInReverseChronologicalOrderFor(USER_ID)).willReturn(posts);

        List<Post> result = retrieveTimeline.execute(USER_ID);

        assertThat(result).isEqualTo(posts);
    } 
    
}