<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.bondarenko.constant.Jsp" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ page import="org.bondarenko.constant.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<!DOCTYPE html>
<html lang="" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Peripress | Sign up</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/materialize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-colors.css">
</head>

<body class="indigo">
<div class="row">
    <div class="col m6 offset-m3 l4 offset-l4 s12">
        <div class="card-panel z-depth-5" style="min-height: 90vh; margin-top: 4vh; position: relative">
            <div class="valign-wrapper">
                <form action="${pageContext.request.contextPath}${Paths.REGISTER}" method="POST" class="center-block" style="text-align: center">
                    <h1><fmt:message key="register" /></h1>
                    <h4><fmt:message key="joinCommunity" /></h4>
                    <% if (request.getAttribute(Jsp.BAD_CREDENTIALS) != null ) {%>
                    <p class="red-text"><fmt:message key="incorrectSignUpForm" /></p>
                    <% } %>
                    <div class="input-field" style="margin-top: 40px">
                        <i class="material-icons prefix tooltipped" data-position="bottom" data-tooltip="<fmt:message key="usernameRequirements" />">account_circle</i>
                        <input type="text" id="username" name="${Jsp.USERNAME}">
                        <label for="username"><fmt:message key="yourUsername" /></label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix tooltipped" data-position="bottom" data-tooltip="<fmt:message key="emailRequirements" />">email</i>
                        <input type="email" id="email" name="${Jsp.EMAIL}">
                        <label for="email"><fmt:message key="yourEmail" /></label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix tooltipped" data-position="bottom" data-tooltip="<fmt:message key="passwordRequirements" />">lock</i>
                        <input type="password" id="password" name="${Jsp.PASSWORD}">
                        <label for="password"><fmt:message key="yourPassword" /></label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix tooltipped" data-position="bottom" data-tooltip="<fmt:message key="confirmPasswordRequirements" />">vpn_key</i>
                        <input type="password" id="confirm-password" name="${Jsp.CONFIRM_PASSWORD}">
                        <label for="confirm-password"><fmt:message key="confirmPassword" /></label>
                    </div>
                    <div class="left-align" style="margin-left: 1vh">
                        <p><strong><fmt:message key="chooseAccountType" /></strong></p>
                        <div class="input-field">
                            <p>
                                <label>
                                    <input class="with-gap" name="${Jsp.ROLE}" type="radio" value="${Role.USER}" checked />
                                    <span><fmt:message key="readerAccount" /></span>
                                </label>
                            </p>
                            <p>
                                <label>
                                    <input class="with-gap" name="${Jsp.ROLE}" type="radio" value="${Role.ADMIN}"/>
                                    <span><fmt:message key="adminAccount" /></span>
                                </label>
                            </p>
                        </div>
                    </div>
                    <p><fmt:message key="haveAccountMessage" /> <a href="${pageContext.request.contextPath}${Paths.LOGIN}"><fmt:message key="logIn" /></a></p>
                    <div class="input-field center">
                        <button class="btn-large indigo darken-4" type="submit"><fmt:message key="createAccount" /></button>
                    </div>
                </form>
                <a style="position: absolute; bottom: 2%" href="${pageContext.request.contextPath}${Paths.HOME}"><fmt:message key="goHome" /></a>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
    $(document).ready(function(){
        $('.tooltipped').tooltip();
    });
</script>
</body>
</html>

