package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Document;
import org.openchat.usecases.GetPostsForUser;
import spark.Request;
import spark.Response;

import java.text.Format;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetDocsForUserAPI {

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

  public String exec(Request req, Response res) {
    String userId = req.params("userId");
    GetPostsForUser getPostsForUser = new GetPostsForUser();
    List<Document> docs = getPostsForUser.exec(APIContext.instance.getUserNameForUUID(userId));
    Collections.sort(docs, this::compare);
    res.status(200);
    res.type("application/json");
    return jsonFor(docs);
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
                    .add("postId", APIContext.instance.getUUIDForMessageId(doc.id))
                    .add("userId", APIContext.instance.getUUIDForUser(doc.username))
                    .add("text", doc.text)
                    .add("dateTime", DATE_TIME_FORMATTER.format(doc.dateTime));

  }
}
