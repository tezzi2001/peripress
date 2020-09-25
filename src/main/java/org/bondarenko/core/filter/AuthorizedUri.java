package org.bondarenko.core.filter;

public class AuthorizedUri {
    private String uri;
    private Role role;

    private AuthorizedUri() {
    }

    public static Builder builder() {
        return new AuthorizedUri().new Builder();
    }

    public Role getRole() {
        return role;
    }

    public String getUri() {
        return uri;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withUri(String uri) {
            AuthorizedUri.this.uri = uri;
            return this;
        }

        public AuthorizedUri hasRole(Role role) {
            AuthorizedUri.this.role = role;
            return build();
        }

        private AuthorizedUri build() {
            return AuthorizedUri.this;
        }
    }
}
