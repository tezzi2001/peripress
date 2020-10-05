package org.bondarenko.core;

import org.bondarenko.command.Command;
import org.bondarenko.command.provider.factory.CommandProvider;
import org.bondarenko.command.provider.factory.Creator;
import org.bondarenko.command.provider.factory.GetCommandProviderCreator;
import org.bondarenko.command.provider.factory.PostCommandProviderCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private static final Creator getCreator = new GetCommandProviderCreator();
    private static final Creator postCreator = new PostCommandProviderCreator();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        execute(request, response, getCreator, uri);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        execute(request, response, postCreator, uri);
    }

    private void execute(HttpServletRequest request, HttpServletResponse response, Creator creator, String uri) throws ServletException, IOException {
        CommandProvider commandProvider = creator.createCommandProvider();
        Command command = commandProvider.provideCommand(uri);
        command.execute(request, response);
    }
}