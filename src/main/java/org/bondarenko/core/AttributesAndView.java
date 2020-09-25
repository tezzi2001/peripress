package org.bondarenko.core;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class AttributesAndView {
    private static final String EMPTY_VIEW = "/empty.jsp";
    private final String view;
    private final Map<String, Object> attributes;

    public AttributesAndView() {
        view = EMPTY_VIEW;
        attributes = new HashMap<>();
    }

    public AttributesAndView(String view) {
        this.view = "/WEB-INF/jsp/" + view + ".jsp";
        attributes = new HashMap<>();
    }

    public void addAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }

    public String getView() {
        return view;
    }

    public void setAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public boolean isEmpty() {
        return view.equals(EMPTY_VIEW);
    }
}