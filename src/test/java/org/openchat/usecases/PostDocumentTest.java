package org.openchat.usecases;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openchat.entities.Document;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PostDocumentTest {

  private PostDocument postDocument;

  @Before
  public void initialize() {
    UseCaseContext.initialize();
    postDocument = new PostDocument();
  }

  @Test
  public void canPostAnyDocument() throws Exception {
    Document createdDocument = postDocument.post("username", "text");
    Document fetchedDocument = UseCaseContext.repository.getDocument(createdDocument.id);
    assertThat(fetchedDocument.username).isEqualTo("username");
    assertThat(fetchedDocument.text).isEqualTo("text");
    assertThat(fetchedDocument.id).isEqualTo(createdDocument.id);
  }

  @Test
  public void canPostAppropriateDocument() throws Exception {
    Document createdDocument = postDocument.postOnlyAppropriateDocument("username", "text");
    Document fetchedDocument = UseCaseContext.repository.getDocument(createdDocument.id);
    assertThat(fetchedDocument.username).isEqualTo("username");
    assertThat(fetchedDocument.text).isEqualTo("text");
    assertThat(fetchedDocument.id).isEqualTo(createdDocument.id);
  }

  @Test(expected = PostDocument.InappropriateException.class)
  @Parameters({
    "orange", "ice cream", "elephant", "I am an elephant in trouble.", "ELEPHANT"
  })
  public void cannotPostInappropriateDocuments(String text) throws Exception {
    postDocument.postOnlyAppropriateDocument("username", text);
  }
}
