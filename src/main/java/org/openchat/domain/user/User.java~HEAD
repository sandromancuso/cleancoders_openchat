package org.openchat.domain.user;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class User {
    private final String userId;
    private final String username;
    private final String password;
    private final String about;

    public User(String userId, String username, String password, String about) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.about = about;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String about() {
        return about;
    }

    public String password() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
