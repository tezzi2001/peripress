package org.bondarenko.core;

import org.bondarenko.command.Command;
import org.bondarenko.command.GetCommandProvider;
import org.bondarenko.command.PostCommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        Command command = GetCommandProvider.getCommand(uri);
        command.execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        Command command = PostCommandProvider.getCommand(uri);
        command.execute(request, response);
    }
}