<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bondarenko.db.entity.PublishingHouse" %>
<%@ page import="java.util.List" %>
<%@ page import="org.bondarenko.constant.Jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<!DOCTYPE html>
<html lang="" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="utf-8">
    <title>Peripress | Subscriptions</title>
    <link rel="shortcut icon" href="img/logo.png" type="image/png">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="css/materialize.css">
    <link rel="stylesheet" href="css/input.css">

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
        .cropped {
            object-fit: cover;
            height: 26px;
            width: 39px;
            float: left;
            margin-right: 10px;
            margin-top: 5px;
        }
        .img {
            height: 60px;
            width: 100px;
            overflow: hidden;
        }
    </style>
</head>

<body>

<jsp:include page="../components/userHeader.jsp" />

<section class="section container">
    <div class="row">
        <% for (PublishingHouse publishingHouse : (List<PublishingHouse>) request.getAttribute(Jsp.PUBLICATION_HOUSES_LIST)) { %>
        <div class="col s12">
            <div class="card hoverable">
                <div class="card-image">
                    <img src="<%= publishingHouse.getMainImage()%>" alt="" class="img">
                </div>
                <div class="card-content">
                    <span class="card-title"  style="margin-bottom: 7%"><%= publishingHouse.getName() %></span>
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

<jsp:include page="../components/footer.jsp" />

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
