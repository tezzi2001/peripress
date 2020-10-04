<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ page import="static org.bondarenko.constant.Session.ROLE" %>
<%@ page import="org.bondarenko.constant.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<header>
    <div class="navbar-fixed">
        <nav class="nav-wrapper transparent">
            <div class="container">
                <a href="${pageContext.request.contextPath}${Paths.HOME}" class="brand-logo indigo-text text-darken-4">
                    <img id="logo" src="${pageContext.request.contextPath}/img/logo.png" alt="logo">
                </a>
                <a href="#" class="sidenav-trigger" data-target="mobile-menu">
                    <i class="material-icons indigo-text text-darken-4">menu</i>
                </a>
                <ul class="right hide-on-med-and-down">
                    <li>
                        <a class="dropdown-trigger btn indigo white-text" href="#" data-target="dropdown-account" style="width: 200px"><fmt:message key="myAccount" /></a>
                        <ul id="dropdown-account" class="dropdown-content">
                            <% if (Role.ADMIN.equals(session.getAttribute(ROLE))) {%>
                            <li>
                                <a href="${pageContext.request.contextPath}${Paths.EDIT_PUBLICATION_HOUSE}" class="indigo-text">
                                    <i class="material-icons indigo-text">create</i>
                                    <fmt:message key="editPublishingHouses" />
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}${Paths.BAN}" class="indigo-text">
                                    <i class="material-icons indigo-text">block</i>
                                    <fmt:message key="banning" />
                                </a>
                            </li>
                            <li class="divider" tabindex="-1"></li>
                            <% } %>
                            <li>
                                <a href="${pageContext.request.contextPath}${Paths.SETTINGS}" class="indigo-text">
                                    <i class="material-icons indigo-text">settings</i>
                                    <fmt:message key="settings" />
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}${Paths.SUBSCRIPTIONS}" class="indigo-text">
                                    <i class="material-icons indigo-text">list</i>
                                    <fmt:message key="subscriptions" />
                                </a>
                            </li>
                            <li>
                                <a href="#lang" class="indigo-text modal-trigger">
                                    <i class="material-icons indigo-text">language</i>
                                    <fmt:message key="language" />
                                </a>
                            </li>
                            <li class="divider" tabindex="-1"></li>
                            <li>
                                <a href="${pageContext.request.contextPath}${Paths.LOGOUT}" class="indigo-text">
                                    <i class="material-icons indigo-text">power_settings_new</i>
                                    <fmt:message key="logout" />
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </div>

    <div>
        <ul class="sidenav indigo lighten-5" id="mobile-menu">
            <li><h3 class="center-align">Peripress</h3></li>
            <hr>
            <li>
                <a href="#">
                    <i class="material-icons">language</i>
                    <fmt:message key="language" />
                </a>
            </li>
            <li>
                <form>
                    <button type="submit" class="btn z-depth-0 white black-text" style="width: 100%; height: 100%" value="ua" ${language == 'ua' ? 'selected' : ''} name="language">
                        <img src="${pageContext.request.contextPath}/img/flags/UA.png" alt="flag of Ukraine" class="cropped">
                        <span style="float: left">Українська</span>
                    </button>
                </form>
            </li>
            <li>
                <form>
                    <button type="submit" class="btn z-depth-0 white black-text" style="width: 100%; height: 100%" value="ru" ${language == 'ru' ? 'selected' : ''} name="language">
                        <img src="${pageContext.request.contextPath}/img/flags/RU.png" alt="flag of Russia" class="cropped">
                        <span style="float: left">Русский</span>
                    </button>
                </form>
            </li>
            <li>
                <form>
                    <button type="submit" class="btn z-depth-0 white black-text" style="width: 100%; height: 100%" value="en" ${language == 'en' ? 'selected' : ''} name="language">
                        <img src="${pageContext.request.contextPath}/img/flags/US.png" alt="flag of United States" class="cropped">
                        <span style="float: left">English (US)</span>
                    </button>
                </form>
            </li>
            <hr>
            <li>
                <a href="${pageContext.request.contextPath}${Paths.SETTINGS}">
                    <i class="material-icons">settings</i>
                    <fmt:message key="settings" />
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}${Paths.SUBSCRIPTIONS}">
                    <i class="material-icons">list</i>
                    <fmt:message key="subscriptions" />
                </a>
            </li>
            <li class="divider" tabindex="-1"></li>
            <li>
                <a href="${pageContext.request.contextPath}${Paths.LOGOUT}" class="indigo-text">
                    <i class="material-icons indigo-text">power_settings_new</i>
                    <fmt:message key="logout" />
                </a>
            </li>
        </ul>
    </div>

    <div class="modal" id="lang" style="margin-top: 7%">
        <h3 class="center" style="margin-top: 20px"><fmt:message key="chooseLanguage" /></h3>
        <ul class="collection">
            <li class="collection-item">
                <form>
                    <button type="submit" class="btn z-depth-0 white black-text" style="width: 100%; height: 100%" value="ua" ${language == 'ua' ? 'selected' : ''} name="language">
                        <img src="${pageContext.request.contextPath}/img/flags/UA.png" alt="flag of Ukraine" class="cropped">
                        <span style="float: left">Українська</span>
                    </button>
                </form>
            </li>
            <li class="collection-item">
                <form>
                    <button type="submit" class="btn z-depth-0 white black-text" style="width: 100%; height: 100%" value="ru" ${language == 'ru' ? 'selected' : ''} name="language">
                        <img src="${pageContext.request.contextPath}/img/flags/RU.png" alt="flag of Russia" class="cropped">
                        <span style="float: left">Русский</span>
                    </button>
                </form>
            </li>
            <li class="collection-item">
                <form>
                    <button type="submit" class="btn z-depth-0 white black-text" style="width: 100%; height: 100%" value="en" ${language == 'en' ? 'selected' : ''} name="language">
                        <img src="${pageContext.request.contextPath}/img/flags/US.png" alt="flag of United States" class="cropped">
                        <span style="float: left">English (US)</span>
                    </button>
                </form>
            </li>
        </ul>
    </div>
</header>
