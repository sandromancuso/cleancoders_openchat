package org.openchat.domain.user;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class RegistrationData {

    private final String username;
    private final String password;
    private final String about;

    public RegistrationData(String username, String password, String about) {
        this.username = username;
        this.password = password;
        this.about = about;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String about() {
        return about;
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }
}
