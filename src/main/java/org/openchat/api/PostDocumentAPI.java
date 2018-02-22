package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.Document;
import org.openchat.usecases.PostDocument;
import spark.Request;
import spark.Response;

public class PostDocumentAPI {
  public String post(Request req, Response res) {
    String userId = req.params("userId");
    String text = Json.parse(req.body()).asObject().getString("text", "");
    PostDocument postDocument = new PostDocument();
    String username = APIContext.instance.getUserNameForUUID(userId);
    try {
      Document doc = postDocument.postOnlyAppropriateDocument(username, text);
      String uuid = APIContext.instance.makeUUIDforID(doc.id);
      res.status(201);
      res.type("application/json");
      JsonObject json = new JsonObject()
                              .add("postId", uuid)
                              .add("userId", APIContext.instance.getUUIDForUser(doc.username))
                              .add("text", doc.text)
                              .add("dateTime", "blah");
      return json.toString();
    } catch (PostDocument.InappropriateException e) {
      res.status(400);
      return "Post contains inappropriate language.";
    }
  }
}
