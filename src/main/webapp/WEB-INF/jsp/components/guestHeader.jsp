<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<header>
    <nav class="nav-wrapper transparent">
        <div class="container">
            <a href="${pageContext.request.contextPath}${Paths.HOME}" class="brand-logo indigo-text text-darken-4">
                <img id="logo" src="img/logo.png" alt="logo">
            </a>
            <a href="#" class="sidenav-trigger" data-target="mobile-menu">
                <i class="material-icons indigo-text text-darken-4">menu</i>
            </a>
            <ul class="right hide-on-med-and-down">
                <li><a id="sign-in" class="indigo-text text-darken-4" href="${pageContext.request.contextPath}${Paths.LOGIN}"><fmt:message key="signIn" /></a></li>
                <li><a class="indigo darken-4 white-text btn" href="${pageContext.request.contextPath}${Paths.REGISTER}"><fmt:message key="startForFree" /></a></li>
                <li>
                    <a class="modal-trigger btn indigo darken-4 white-text" href="#lang">
                        <fmt:message key="language" />
                    </a>
                </li>
            </ul>
        </div>
    </nav>

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
            <li><a href="${pageContext.request.contextPath}${Paths.LOGIN}"><fmt:message key="signIn" /></a></li>
            <li><a href="${pageContext.request.contextPath}${Paths.REGISTER}"><fmt:message key="startForFree" /></a></li>
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
