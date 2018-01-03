package org.openchat.core.domain.user;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class User {
    private final String id;
    private final String username;
    private final String password;
    private final String about;

    public User(String id, String username, String password, String about) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.about = about;
    }

    public String id() {
        return this.id;
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

    public boolean matches(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return reflectionToString(this, MULTI_LINE_STYLE);
    }
}
