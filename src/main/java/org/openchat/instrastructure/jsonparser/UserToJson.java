package org.openchat.instrastructure.jsonparser;

import com.eclipsesource.json.JsonObject;
import org.openchat.domain.user.User;

public class UserToJson {

    public static String jsonFor(User user) {
        return new JsonObject()
                        .add("userId", user.userId())
                        .add("username", user.username())
                        .add("about", user.about())
                        .toString();
    }
}
