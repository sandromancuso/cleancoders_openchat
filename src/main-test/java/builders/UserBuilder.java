package builders;

import org.openchat.domain.user.User;
import org.openchat.infrastructure.IDGenerator;

public class UserBuilder {

    private String id = new IDGenerator().nextId();
    private String username = "User Name";
    private String password = "23lkjs34";
    private String about = "About the user";

    public UserBuilder() {
    }

    public UserBuilder(User user) {
        this.id = user.id();
        this.username = user.username();
        this.password = user.password();
        this.about = user.about();
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public static UserBuilder aCloneFrom(User user) {
        return new UserBuilder(user);
    }

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withAbout(String about) {
        this.about = about;
        return this;
    }

    public User build() {
        return new User(id, username, password, about);
    }
}
