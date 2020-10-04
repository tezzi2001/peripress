package org.bondarenko.core;

import org.bondarenko.constant.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bondarenko.constant.Pages.FORBIDDEN;
import static org.bondarenko.constant.Paths.*;
import static org.bondarenko.constant.Session.ROLE;

@WebFilter("/*")
public class DispatcherFilter implements Filter {
    private static final Map<String, Role> PROTECTED_URIS = new HashMap<>();
    private static final List<String> GUEST_URIS = new ArrayList<>();

    static {
        GUEST_URIS.add(LOGIN);
        GUEST_URIS.add(REGISTER);

        PROTECTED_URIS.put(SUBSCRIBE, Role.USER);
        PROTECTED_URIS.put(SEARCH, Role.USER);
        PROTECTED_URIS.put(FILTER_AND_SORT, Role.USER);
        PROTECTED_URIS.put(SUBSCRIPTIONS, Role.USER);
        PROTECTED_URIS.put(DEPOSIT, Role.USER);
        PROTECTED_URIS.put(LOGOUT, Role.USER);
        PROTECTED_URIS.put(SETTINGS, Role.USER);

        PROTECTED_URIS.put(BAN, Role.ADMIN);
        PROTECTED_URIS.put(ADD_PUBLICATION_HOUSE, Role.ADMIN);
        PROTECTED_URIS.put(REMOVE_PUBLICATION_HOUSE, Role.ADMIN);
        PROTECTED_URIS.put(EDIT_PUBLICATION_HOUSE, Role.ADMIN);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String uri = httpServletRequest.getRequestURI();
        Role currentRole = PROTECTED_URIS.get(uri);
        Role sessionRole = (Role) session.getAttribute(ROLE);

        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        if (Role.ADMIN.equals(sessionRole)) {
            chain.doFilter(request, response);
            return;
        }

        if (GUEST_URIS.contains(uri) && sessionRole != null) {
            request.getRequestDispatcher(HOME).forward(request, response);
            return;
        }

        if (PROTECTED_URIS.containsKey(uri)) {
            if (sessionRole == null) {
                request.getRequestDispatcher(LOGIN).forward(request, response);
            }
            if (!currentRole.equals(sessionRole)) {
                request.getRequestDispatcher(FORBIDDEN).forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }
}
