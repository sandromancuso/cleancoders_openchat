package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Document;
import org.openchat.usecases.GetSubscribedDocuments;
import spark.Request;
import spark.Response;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.openchat.api.APIContext.instance;

public class GetSubscribedDocumentsAPI {
  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

  public String exec(Request req, Response res) {
    String userId = req.params("userId");
    GetSubscribedDocuments getSubscribedDocuments = new GetSubscribedDocuments();
    List<Document> documents = getSubscribedDocuments.exec(instance.getUserNameForUUID(userId));
    Collections.sort(documents, this::compare);
    res.status(200);
    res.type("application/json");
    return jsonFor(documents);
  }

  private int compare(Document d1, Document d2) {
    return Long.compare(d2.id, d1.id);
  }

  private String jsonFor(List<Document> docs) {
    JsonArray json = new JsonArray();
    docs.forEach(doc -> json.add(jsonObjectFor(doc)));
    return json.toString();
  }

  private JsonObject jsonObjectFor(Document doc) {
    return new JsonObject()
                    .add("postId", instance.getUUIDForMessageId(doc.id))
                    .add("userId", instance.getUUIDForUser(doc.username))
                    .add("text", doc.text)
                    .add("dateTime", DATE_TIME_FORMATTER.format(doc.dateTime));

  }


}
