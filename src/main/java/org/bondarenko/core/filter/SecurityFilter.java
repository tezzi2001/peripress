package org.bondarenko.core.filter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter {
    private final List<AuthorizedUri> authorizedUris = new ArrayList<>();
    private String signInPath = "/";

    private SecurityFilter() {
    }

    public static Builder builder() {
        return new SecurityFilter().new Builder();
    }

    public void filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for (AuthorizedUri authorizedUri : authorizedUris) {
            String uri = authorizedUri.getUri();
            if (uri.endsWith("*")) {
                uri = trimUri(uri);
            }
            if (request.getRequestURI().startsWith(uri)) {
                authorize(request, response, authorizedUri.getRole());
            }
        }
    }

    private String trimUri(String uri) {
        uri = uri.substring(0, uri.length() - 2);
        if (uri.equals("")) {
            uri = "/";
        }
        return uri;
    }

    private void authorize(HttpServletRequest request, HttpServletResponse response, Role role) throws ServletException, IOException {
        List<String> roles = (List<String>) request.getSession().getAttribute("role");

        if (roles == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(signInPath);
            dispatcher.forward(request, response);
            return;
        }

        if (!roles.contains(role.toString())) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/403.jsp");
            dispatcher.forward(request, response);
        }
    }

    public class Builder {
        private Builder() {
        }

        public Builder withSignInPath(String signInPath) {
            SecurityFilter.this.signInPath = signInPath;
            return this;
        }

        public Builder withUrlMatcher(AuthorizedUri authorizedUri) {
            authorizedUris.add(authorizedUri);
            return this;
        }

        public SecurityFilter build() {
            return SecurityFilter.this;
        }
    }
}
