<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bondarenko.db.entity.PublishingHouse" %>
<%@ page import="java.util.List" %>
<%@ page import="org.bondarenko.core.filtering.Theme" %>
<%@ page import="org.bondarenko.core.filtering.Type" %>
<%@ page import="org.bondarenko.core.sorting.SortingOption" %>
<%@ page import="org.bondarenko.core.sorting.SortingType" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ page import="org.bondarenko.constant.Jsp" %>
<%@ page import="java.util.function.Supplier" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<!DOCTYPE html>
<html lang="${language}" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="utf-8">
    <title>Peripress | Home</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/materialize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-colors.css">

    <style>
        nav {
            backdrop-filter: blur(5px);
        }
        #logo {
            height: 40px;
            margin: 12px;
        }
        body {
            display: flex;
            min-height: 100vh;
            flex-direction: column;
        }
        section {
            flex: 1 0 auto;
        }
        .img {
            height: 160px;
            overflow: hidden;
        }
        .cropped {
            object-fit: cover;
            height: 26px;
            width: 39px;
            float: left;
            margin-right: 10px;
            margin-top: 5px;
        }
    </style>
</head>

<body>

<jsp:include page="components/userHeader.jsp" />

<section class="section container center" style="padding-bottom: 0">
    <form action="${pageContext.request.contextPath}${Paths.SEARCH}" method="post">
        <div class="inline input-field" style="width: 70%; margin-bottom: 0">
            <input name="${Jsp.SEARCH}" type="text" id="search">
            <label for="search"><fmt:message key="searchMessage" /></label>
        </div>
        <button type="submit" class="btn white indigo-text text-darken-4 z-depth-0"><strong><fmt:message key="search" /></strong></button>
    </form>
</section>

<section class="section container" style="padding-top: 0; margin-bottom: 50px">
    <form action="${pageContext.request.contextPath}${Paths.FILTER_AND_SORT}" method="get">
        <ul class="collapsible">
            <li>
                <div class="collapsible-header">
                    <i class="material-icons">filter_list</i>
                    <fmt:message key="filters" />
                </div>
                <div class="collapsible-body">
                    <div class="row">
                        <div class="col s6">
                            <select name="${Jsp.THEMES}" multiple>
                                <option value="" disabled selected><fmt:message key="pickThemes" /></option>
                                <option value="${Theme.SCIENCE}"><fmt:message key="science" /></option>
                                <option value="${Theme.NATURE}"><fmt:message key="nature" /></option>
                                <option value="${Theme.ART}"><fmt:message key="art" /></option>
                            </select>
                        </div>
                        <div class="col s6">
                            <select name="${Jsp.TYPES}" multiple>
                                <option value="" disabled selected><fmt:message key="pickTypes" /></option>
                                <option value="${Type.NEWSPAPER}"><fmt:message key="newspaper" /></option>
                                <option value="${Type.MAGAZINE}"><fmt:message key="magazine" /></option>
                                <option value="${Type.SCIENTIFIC_JOURNAL}"><fmt:message key="scientificJournal" /></option>
                                <option value="${Type.YEARBOOK}"><fmt:message key="yearbook" /></option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s12">
                            <p>
                                <label>
                                    <input name="${Jsp.IS_FREE}" type="checkbox" value="${Jsp.IS_FREE_DEFAULT_VALUE}"/>
                                    <span><fmt:message key="onlyFree" /></span>
                                </label>
                            </p>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="collapsible-header"><i class="material-icons">sort</i><fmt:message key="sorters" /></div>
                <div class="collapsible-body">
                        <p>
                            <label>
                                <input class="with-gap" name="${Jsp.SORTING_TYPE}" type="radio" value="${SortingType.ASCENDING}" />
                                <span><fmt:message key="ascending" /></span>
                            </label>
                        </p>
                        <p>
                            <label>
                                <input class="with-gap" name="${Jsp.SORTING_TYPE}" type="radio" value="${SortingType.DESCENDING}" />
                                <span><fmt:message key="descending" /></span>
                            </label>
                        </p>
                        <hr>
                        <p>
                            <label>
                                <input class="with-gap" name="${Jsp.SORTING_OPTION}" type="radio" value="${SortingOption.BY_NAME}"/>
                                <span><fmt:message key="byName" /></span>
                            </label>
                        </p>
                        <p>
                            <label>
                                <input class="with-gap" name="${Jsp.SORTING_OPTION}" type="radio" value="${SortingOption.BY_PRICE}"/>
                                <span><fmt:message key="byPrice" /></span>
                            </label>
                        </p>
                        <p>
                            <label>
                                <input class="with-gap" name="${Jsp.SORTING_OPTION}" type="radio" value="${SortingOption.BY_SUBSCRIPTIONS}"/>
                                <span><fmt:message key="bySubscriptions" /></span>
                            </label>
                        </p>
                </div>
            </li>
            <li style="align-content: center">
                <div class="collapsible-header">
                    <button type="submit" class="btn indigo darken-4" style="width: 100%">
                        <strong><fmt:message key="search" /></strong>
                    </button>
                </div>
            </li>
        </ul>
    </form>
</section>

<section class="section container">
    <div class="row">
        <% for (PublishingHouse publishingHouse : (List<PublishingHouse>) request.getAttribute(Jsp.PUBLICATION_HOUSES_LIST)) { %>
        <div class="col m5 s12 offset-m1">
            <div class="card hoverable">
                <div class="card-image">
                    <img src="<%= publishingHouse.getMainImage()%>" alt="" class="img">
                    <a href="${pageContext.request.contextPath}${Paths.SUBSCRIBE}?${Jsp.PUBLISHING_HOUSE_ID}=<%=publishingHouse.getId()%>" class="halfway-fab btn-floating blue pulse">
                        <i class="material-icons">add</i>
                    </a>
                </div>
                <div class="card-content">
                    <span class="card-name" style="margin-bottom: 7%"><%= publishingHouse.getName() %></span>
                    <p style="height: 65px"><%= publishingHouse.getDescription() %></p>
                </div>
                <div class="card-action">
                    <div class="row" style="margin-bottom: 0">
                        <div class="col s1">
                            <i class="material-icons blue-text">local_offer</i>
                        </div>
                        <div class="col s1 offset-s1 offset-m3 offset-m4">
                            <span class="badge"><%= publishingHouse.getSubscriptsCount() %></span>
                        </div>
                    </div>
                    <% if (publishingHouse.getSubscriptionPriceUsd() != 0) { %>
                    <div class="row" style="margin-bottom: 0">
                        <div class="col s1">
                            <i class="material-icons red-text">attach_money</i>
                        </div>
                        <div class="col s1 offset-s1 offset-m3 offset-m4">
                            <span class="badge"><%= publishingHouse.getSubscriptionPriceUsd() %>$</span>
                        </div>
                    </div>
                    <% } else { %>
                    <div class="row" style="margin-bottom: 0">
                        <div class="col s1">
                            <i class="material-icons green-text">money_off</i>
                        </div>
                    </div>
                    <% } %>
                    <div class="row" style="margin-bottom: 0">
                        <div class="col s1">
                            <strong class="indigo-text"><fmt:message key="type" /></strong>
                        </div>
                        <div class="col s1 offset-s1 offset-m3 offset-m4">
                            <strong class="blue-text"><%= publishingHouse.getType() %></strong>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 0">
                        <div class="col s1">
                            <strong class="indigo-text"><fmt:message key="theme" /></strong>
                        </div>
                        <div class="col s1 offset-s1 offset-m3 offset-m4">
                            <strong class="blue-text"><%= publishingHouse.getTheme() %></strong>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</section>

<section class="section container center">
    <%
        Object pageNumberObj = request.getAttribute(Jsp.PAGE_PARAM);
        int pageNumber = pageNumberObj == null ? 1 : Integer.parseInt(String.valueOf(pageNumberObj));

        Object paginationPathObj = request.getAttribute(Jsp.PAGINATION_PATH);
        String paginationPath = paginationPathObj == null ? Paths.HOME : String.valueOf(paginationPathObj);

        Object paginationQueryObj = request.getAttribute(Jsp.PAGINATION_QUERY);
        Supplier<String> query = () -> {
            String string = String.valueOf(paginationQueryObj);
            if (string.contains(Jsp.PAGE_PARAM)) {
                string = string
                        .substring(0, string.length()-2)
                        .replace(Jsp.PAGE_PARAM, "");
                string = string.substring(0, string.length()-1);
            }
            if (string.isEmpty()) {
                return "?";
            } else {
                return '?'+string+'&';
            }
        };
        String paginationQuery = paginationQueryObj == null ? "?" : query.get();
    %>
    <c:set var="pageNumber" value="<%= pageNumber %>" scope="request" />
    <c:set var="paginationPath" value="<%= paginationPath %>" scope="request" />
    <c:set var="paginationQuery" value="<%= paginationQuery %>" scope="request" />
    <c:set var="pageRequest" value="${pageContext.request.contextPath}${paginationPath}${paginationQuery}${Jsp.PAGE_PARAM}" scope="request"/>
    <ul class="pagination">
        <c:if test = "${pageNumber == 1}">
            <li class="disabled"><a><i class="material-icons">chevron_left</i></a></li>
            <li class="active indigo"><a href="${pageRequest}=1">1</a></li>
            <li class="waves-effect"><a href="${pageRequest}=2">2</a></li>
            <li class="waves-effect"><a href="${pageRequest}=3">3</a></li>
            <li class="waves-effect"><a href="${pageRequest}=4">4</a></li>
            <li class="waves-effect"><a href="${pageRequest}=5">5</a></li>
            <li class="waves-effect"><a href="${pageRequest}=2"><i class="material-icons">chevron_right</i></a></li>
        </c:if>
        <c:if test = "${pageNumber == 2}">
            <li class="waves-effect"><a href="${pageRequest}=1"><i class="material-icons">chevron_left</i></a></li>
            <li class="waves-effect"><a href="${pageRequest}=1">1</a></li>
            <li class="active indigo"><a href="${pageRequest}=2">2</a></li>
            <li class="waves-effect"><a href="${pageRequest}=3">3</a></li>
            <li class="waves-effect"><a href="${pageRequest}=4">4</a></li>
            <li class="waves-effect"><a href="${pageRequest}=5">5</a></li>
            <li class="waves-effect"><a href="${pageRequest}=3"><i class="material-icons">chevron_right</i></a></li>
        </c:if>
        <c:if test = "${pageNumber >= 3}">
            <li class="waves-effect"><a href="${pageRequest}=${pageNumber-1}"><i class="material-icons">chevron_left</i></a></li>
            <li class="waves-effect"><a href="${pageRequest}=${pageNumber-2}">${pageNumber-2}</a></li>
            <li class="waves-effect"><a href="${pageRequest}=${pageNumber-1}">${pageNumber-1}</a></li>
            <li class="active indigo"><a href="${pageRequest}=${pageNumber}">${pageNumber}</a></li>
            <li class="waves-effect"><a href="${pageRequest}=${pageNumber+1}">${pageNumber+1}</a></li>
            <li class="waves-effect"><a href="${pageRequest}=${pageNumber+2}">${pageNumber+2}</a></li>
            <li class="waves-effect"><a href="${pageRequest}=${pageNumber+1}"><i class="material-icons">chevron_right</i></a></li>
        </c:if>
    </ul>
</section>

<jsp:include page="components/footer.jsp" />

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<script>
    $(document).ready(function(){
        $('.collapsible').collapsible();
        $('.sidenav').sidenav();
        $('.dropdown-trigger').dropdown();
        $('select').formSelect();
        $('.modal').modal();
        $('.modal-trigger').leanModal();
    });
</script>
</body>
</html>
