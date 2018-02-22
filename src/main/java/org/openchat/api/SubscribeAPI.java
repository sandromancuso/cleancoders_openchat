package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.openchat.usecases.Subscribe;
import spark.Request;
import spark.Response;

import static org.openchat.api.APIContext.instance;

public class SubscribeAPI {
  public String exec(Request req, Response res) {
    JsonObject json = Json.parse(req.body()).asObject();
    String subscriberId = json.getString("followerId", "");
    String authorId = json.getString("followeeId", "");
    String subscriberName = instance.getUserNameForUUID(subscriberId);
    String authorName = instance.getUserNameForUUID(authorId);
    Subscribe subscribe = new Subscribe();
    boolean added = subscribe.exec(subscriberName, authorName);
    if (added) {
      res.status(201);
    } else {
      res.status(400);
    }
    return "";
  }

}
