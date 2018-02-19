package org.openchat.domain.posts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.domain.users.IdGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceShould {

    private static final String POSTID = UUID.randomUUID().toString();
    private static final String USERID = UUID.randomUUID().toString();
    private static final String TEXT = "text";
    private static final LocalDateTime DATE_TIME = LocalDateTime.now();

    private static final Post NEW_POST = new Post(POSTID, USERID, "text", DATE_TIME);

    @Mock IdGenerator idGenerator;
    @Mock Clock clock;
    @Mock PostRepository postRepository;
    @Mock LanguageService languageService;

    PostService service;

    @Before
    public void initialise() {
        service = new PostService(languageService, idGenerator, clock, postRepository);
    }

    @Test public void
    create_post() throws InappropriateLanguageException {
        given(idGenerator.next()).willReturn(POSTID);
        given(clock.now()).willReturn(DATE_TIME);

        Post post = service.createPost(USERID, TEXT);

        verify(postRepository).add(NEW_POST);
        assertThat(post).isEqualTo(NEW_POST);
    }

    @Test(expected = InappropriateLanguageException.class) public void
    throw_exception_when_creating_post_with_inappropriate_language() throws InappropriateLanguageException {
        given(languageService.isInappropriate(TEXT)).willReturn(true);

        service.createPost(USERID, TEXT);
    }

}