package org.openchat.domain.user;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public  class LoginData {

    private final String username;
    private final String password;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return reflectionEquals(this, other);
    }

}