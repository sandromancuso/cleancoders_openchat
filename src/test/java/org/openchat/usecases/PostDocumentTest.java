package org.openchat.usecases;

import org.junit.Before;
import org.junit.Test;
import org.openchat.entities.Document;

import static org.assertj.core.api.Assertions.assertThat;

public class PostDocumentTest {
  @Before
  public void initialize() {
    UseCaseContext.initialize();
  }

  @Test
  public void canPostDocument() throws Exception {
    PostDocument postDocument = new PostDocument();
    Document createdDocument = postDocument.post("username", "text");
    Document fetchedDocument = UseCaseContext.repository.getDocument(createdDocument.id);
    assertThat(fetchedDocument.username).isEqualTo("username");
    assertThat(fetchedDocument.text).isEqualTo("text");
    assertThat(fetchedDocument.id).isEqualTo(createdDocument.id);
  }
}
