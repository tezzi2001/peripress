package org.bondarenko.command.impl;

import org.bondarenko.command.Command;
import org.bondarenko.core.filtering.FilteringOptions;
import org.bondarenko.core.filtering.Theme;
import org.bondarenko.core.filtering.Type;
import org.bondarenko.core.sorting.SortingOption;
import org.bondarenko.core.sorting.SortingOptions;
import org.bondarenko.core.sorting.SortingType;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.bondarenko.constant.Jsp.*;
import static org.bondarenko.constant.Pages.PUBLICATION_HOUSES;

public class FilterAndSortCommand implements Command {
    private final UserService userService;

    public FilterAndSortCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Theme> themes = new ArrayList<>();
        String[] themesStrings = request.getParameterValues(THEMES);
        if (themesStrings != null) {
            for (String theme : themesStrings) {
                themes.add(Theme.valueOf(theme));
            }
        }

        List<Type> types = new ArrayList<>();
        String[] typesStrings = request.getParameterValues(TYPES);
        if (typesStrings != null) {
            for (String type : typesStrings) {
                types.add(Type.valueOf(type));
            }
        }

        boolean isFree = IS_FREE_DEFAULT_VALUE.equals(request.getParameter(IS_FREE));
        FilteringOptions filteringOptions = new FilteringOptions(themes, types, isFree);

        Optional<String> option = Optional.ofNullable(request.getParameter(SORTING_OPTION));
        String sortingType = request.getParameter(SORTING_TYPE);
        SortingOptions sortingOptions = new SortingOptions(
                SortingType.DESCENDING.toString().equals(sortingType),
                SortingOption.valueOf(option.orElse(SortingOption.NO_OPTION.toString()))
        );

        String pageAsString = request.getParameter(PAGE_PARAM);
        int page;
        try {
            page = Integer.parseInt(pageAsString);
        } catch (NumberFormatException e) {
            page = 1;
        }

        List<PublishingHouse> publishingHouses = userService.getAllPublishingHouses(filteringOptions, sortingOptions, page);
        request.setAttribute(PUBLICATION_HOUSES_LIST, publishingHouses);
        request.setAttribute(PAGE_PARAM, page);
        request.setAttribute(PAGINATION_PATH, request.getRequestURI());
        request.setAttribute(PAGINATION_QUERY, request.getQueryString());
        request.getRequestDispatcher(PUBLICATION_HOUSES).forward(request, response);
    }
}
