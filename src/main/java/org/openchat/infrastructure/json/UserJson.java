package org.openchat.infrastructure.json;

import com.eclipsesource.json.JsonObject;
import org.openchat.domain.users.User;

public class UserJson {
    public static String jsonFor(User user) {
        return new JsonObject()
                        .add("id", user.id())
                        .add("username", user.username())
                        .add("about", user.about())
                        .toString();
    }
}
