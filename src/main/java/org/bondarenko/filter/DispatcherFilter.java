package org.bondarenko.filter;

import org.bondarenko.core.filter.Role;
import org.bondarenko.core.filter.SecurityFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.bondarenko.core.filter.AuthorizedUri.builder;

@WebFilter("/*")
public class DispatcherFilter implements Filter {
    private final SecurityFilter securityFilter;

    public DispatcherFilter() {
        securityFilter = SecurityFilter.builder()
                .withSignInPath("/sign-in")
                .withUrlMatcher(builder().withUri("/page-list").hasRole(Role.USER))
                .build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        securityFilter.filter((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
    }
}
