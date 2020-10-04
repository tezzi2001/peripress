<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.bondarenko.constant.Jsp" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<!DOCTYPE html>
<html lang="" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Peripress | Sign in</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/materialize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-colors.css">
</head>

<body class="indigo">
<div class="row">
    <div class="col m6 offset-m3 l4 offset-l4 s12">
        <div class="card-panel z-depth-5" style="min-height: 90vh; margin-top: 4vh; position: relative">
            <div class="valign-wrapper" style="margin-top: 20%">
                <form action="${pageContext.request.contextPath}${Paths.LOGIN}" method="POST" class="center-block" style="text-align: center; min-width: 80%">
                    <h1><fmt:message key="logIn" /></h1>
                    <h4><fmt:message key="welcomeBack" /></h4>
                    <% if (request.getAttribute(Jsp.BAD_CREDENTIALS) != null ) {%>
                    <p class="red-text"><fmt:message key="incorrectSignInForm" /></p>
                    <% } %>
                    <div class="input-field" style="margin-top: 40px">
                        <i class="material-icons prefix">account_circle</i>
                        <input type="text" id="username" name="${Jsp.USERNAME}">
                        <label for="username"><fmt:message key="yourUsername" /></label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <input type="password" id="password" name="${Jsp.PASSWORD}">
                        <label for="password"><fmt:message key="yourPassword" /></label>
                    </div>
                    <p><fmt:message key="noAccountMessage" /> <a href="${pageContext.request.contextPath}${Paths.REGISTER}"><fmt:message key="register" /></a></p>
                    <p><fmt:message key="forgotPassword" /> <a class="modal-trigger" href="#reset-password"><fmt:message key="resetPassword" /></a></p>
                    <div class="input-field center">
                        <button class="btn-large indigo darken-4" type="submit"><fmt:message key="logIn" /></button>
                    </div>
                </form>
                <a style="position: absolute; bottom: 2%" href="${pageContext.request.contextPath}${Paths.HOME}"><fmt:message key="goHome" /></a>
            </div>
        </div>
    </div>
</div>

<div class="modal row" id="reset-password" style="margin-top: 7%">
    <div class="modal-content col s12 m6 offset-m3" style="text-align: center">
        <form action="${pageContext.request.contextPath}${Paths.RESET_PASSWORD}" method="post">
            <h3><fmt:message key="resetPassword" /></h3>
            <p><fmt:message key="emailInstructionsMessage" /></p>
            <div class="input-field center">
                <i class="material-icons prefix">email</i>
                <input type="email" id="email" name="${Jsp.EMAIL}">
                <label for="email"><fmt:message key="yourEmail" /></label>
            </div>
            <div class="input-field center">
                <button class="btn-large indigo darken-4" type="submit"><fmt:message key="resetPassword" /></button>
            </div>
        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
    $(document).ready(function(){
        $('.modal').modal();
    });
</script>
</body>
</html>

