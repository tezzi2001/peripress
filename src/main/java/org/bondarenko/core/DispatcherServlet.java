package org.bondarenko.core;

import org.bondarenko.core.annotation.Controller;
import org.bondarenko.core.annotation.Mapping;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private final Map<String, String> controllers = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init() throws ServletException {
        Reflections reflections = new Reflections("org.bondarenko", new SubTypesScanner(false));
        Set<String> controllerNames = reflections.getAllTypes();
        for (String name : controllerNames) {
            try {
                Class<?> clazz = Class.forName(name);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    Controller controllerAnnotation = clazz.getAnnotation(Controller.class);
                    controllers.put(controllerAnnotation.value(), name);
                }
            } catch (ClassNotFoundException e) {
                LOGGER.warn("", e);
            }
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        RequestProcessor requestProcessor = new RequestProcessor(method, uri, request, response);
        AttributesAndView attributesAndView = requestProcessor.process();

        RequestDispatcher dispatcher = request.getRequestDispatcher(attributesAndView.getView());
        attributesAndView.setAttributes(request);
        dispatcher.forward(request, response);
    }

    private class RequestProcessor {
        private final String webMethod;
        private String uri;
        private final HttpServletRequest request;
        private final HttpServletResponse response;

        private RequestProcessor(String webMethod, String uri, HttpServletRequest request, HttpServletResponse response) {
            this.webMethod = webMethod;
            this.uri = uri;
            this.request = request;
            this.response = response;
        }

        private AttributesAndView process() {
            for (Map.Entry<String, String> entry : controllers.entrySet()) {
                String fullRequestUri = getFullPath(uri);
                String fullControllerUri = getFullPath(entry.getKey());

                if (fullRequestUri.startsWith(fullControllerUri)) {
                    prepareUri(entry.getKey());
                    AttributesAndView attributesAndView = invokeControllerMethod(entry.getValue());
                    if (!attributesAndView.isEmpty()) {
                        return attributesAndView;
                    }
                }
            }

            response.setStatus(404);
            return new AttributesAndView();
        }

        private String getFullPath(String path) {
            return path.equals("/") ? "/" : path + "/";
        }

        private void prepareUri(String controllerUri) {
            uri = uri.replaceFirst(controllerUri, "");
            if ("".equals(uri)) {
                uri = "/";
            } else {
                if (!uri.startsWith("/")) {
                    uri = "/" + uri;
                }
            }
        }

        private AttributesAndView invokeControllerMethod(String controllerName) {
            try {
                Class<?> clazz = Class.forName(controllerName);
                Constructor<?> constructor = clazz.getConstructor();
                Object controller = constructor.newInstance();
                Method[] methods = controller.getClass().getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Mapping.class) && webMethod.equals(method.getAnnotation(Mapping.class).method()) && uri.equals(method.getAnnotation(Mapping.class).url())) {
                        Object[] parameters = getParameters(method);
                        return (AttributesAndView) method.invoke(controller, parameters);
                    }
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                LOGGER.warn("", e);
            }

            return new AttributesAndView();
        }

        Object[] getParameters(Method method) {
            List<Object> parameters = new ArrayList<>();
            Class<?>[] types = method.getParameterTypes();

            for (Class<?> type : types) {
                if (type.equals(HttpServletRequest.class)) {
                    parameters.add(request);
                }
                if (type.equals(HttpServletResponse.class)) {
                    parameters.add(response);
                }
            }

            return parameters.toArray();
        }
    }
}