package org.bondarenko.controller;

import org.bondarenko.core.AttributesAndView;
import org.bondarenko.core.annotation.Controller;
import org.bondarenko.core.annotation.Mapping;
import org.bondarenko.service.AuthService;
import org.bondarenko.service.implementation.AuthServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.HttpMethod;

@Controller("/")
public class HomeController {
    private final AuthService authService;

    public HomeController() {
        this.authService = new AuthServiceImpl();
    }

    @Mapping(method = HttpMethod.GET, url = "/")
    public AttributesAndView getHome() {
        return new AttributesAndView("home");
    }

    @Mapping(method = HttpMethod.GET, url = "/sign-in")
    public AttributesAndView getSignInPage() {
        return new AttributesAndView("signIn");
    }

    @Mapping(method = HttpMethod.POST, url = "/sign-in")
    public AttributesAndView signIn(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        authService.login(username, password, session);
        return new AttributesAndView("home");
    }

    @Mapping(method = HttpMethod.GET, url = "/sign-up")
    public AttributesAndView getSignUpPage() {
        return new AttributesAndView("signUp");
    }

    @Mapping(method = HttpMethod.POST, url = "/sign-up")
    public AttributesAndView signUp() {
        return new AttributesAndView("signIn");
    }

    @Mapping(method = HttpMethod.POST, url = "/reset-password")
    public AttributesAndView resetPassword() {
        return new AttributesAndView("signIn");
    }

    @Mapping(method = HttpMethod.GET, url = "/sign-out")
    public AttributesAndView singOut() {
        return new AttributesAndView("home");
    }
}
