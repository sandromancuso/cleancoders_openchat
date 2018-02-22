package org.openchat.api;

import com.eclipsesource.json.Json;
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
      postDocument.postOnlyAppropriateDocument(username, text);
      res.status(201);
      res.type("application/json");
      return "";
    } catch (PostDocument.InappropriateException e) {
      res.status(400);
      return "Post contains inappropriate language.";
    }
  }
}
