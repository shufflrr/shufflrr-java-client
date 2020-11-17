package com.shufflrr.sdk;

import java.net.URI;

/**
 * To be replaced with OAuth when the functionality is available.
 */
@Deprecated()
public final class Credentials {
    private final String site, username, password;

    public static Builder builder() {
        return new Builder();
    }

    private Credentials(String site, String username, String password) {
        this.site = site;
        this.username = username;
        this.password = password;
    }

    public String site() {
        return this.site;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    public URI uri() {
        return URI.create(String.format("https://%s.shufflrr.com/", site()));
    }

    public String asJson() {
        return String.format("{\"emailAddress\":\"%s\",\"password\":\"%s\",\"keepLoggedIn\":true}", this.username(), this.password());
    }

    public static final class Builder {
        private String site, username, password;

        private Builder() {}

        public Builder withSite(String site) {
            this.site = site;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Credentials build() {
            if (site != null && username != null && password != null) {
                return new Credentials(site, username, password);
            } else {
                throw new IllegalStateException("Site name, username, and password required.");
            }
        }
    }
}