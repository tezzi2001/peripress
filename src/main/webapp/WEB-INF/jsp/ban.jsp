<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ page import="org.bondarenko.constant.Jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<!DOCTYPE html>
<html lang="${language}" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Peripress | Banning</title>
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

<section class="section container">
    <form method="post" action="${pageContext.request.contextPath}${Paths.BAN}">
        <h6>
            <em><fmt:message key="banMessage" /></em>
        </h6>
        <div class="input-field" style="display: flex; width: 40%">
            <input type="number" min="1" placeholder="<fmt:message key="banPlaceholderMessage" />" name="${Jsp.BAN_ID}">
            <button class="material-icons" style="margin-top: 15px" type="submit">save</button>
        </div>
    </form>


    <form method="post" action="${pageContext.request.contextPath}${Paths.UNBAN}">
        <h6>
            <em><fmt:message key="unbanMessage" /></em>
        </h6>
        <div class="input-field" style="display: flex; width: 40%">
            <input type="number" min="1" placeholder="<fmt:message key="unbanPlaceholderMessage" />" name="${Jsp.BAN_ID}">
            <button class="material-icons" style="margin-top: 15px" type="submit">save</button>
        </div>
    </form>
</section>

<jsp:include page="components/footer.jsp" />

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<script>
    $(document).ready(function(){
        $('.sidenav').sidenav();
        $('.dropdown-trigger').dropdown();
        $('.modal').modal();
    });
</script>
</body>
</html>
