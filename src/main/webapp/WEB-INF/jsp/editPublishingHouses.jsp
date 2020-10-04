<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ page import="org.bondarenko.constant.Jsp" %>
<%@ page import="org.bondarenko.core.filtering.Theme" %>
<%@ page import="org.bondarenko.core.filtering.Type" %>
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
    <div class="row">
        <div class="col m5 s12 offset-m1">
            <form method="post" action="${pageContext.request.contextPath}${Paths.ADD_PUBLICATION_HOUSE}">
                <h5>
                    <em><fmt:message key="createPublication" /></em>
                </h5>
                <input type="text" placeholder="<fmt:message key="namePlaceholderMessage" />" name="${Jsp.PUBLISHING_HOUSE_NAME}">
                <input type="text" placeholder="<fmt:message key="descriptionPlaceholderMessage" />" name="${Jsp.PUBLISHING_HOUSE_DESCRIPTION}">
                <input type="text" placeholder="<fmt:message key="imageLinkPlaceholderMessage" />" name="${Jsp.PUBLISHING_HOUSE_IMAGE}">
                <input type="number" placeholder="<fmt:message key="subscriptionPricePlaceholderMessage" />" name="${Jsp.PUBLISHING_HOUSE_PRICE}">
                <div class="input-field">
                    <select name="${Jsp.PUBLISHING_HOUSE_THEME}">
                        <option value="" disabled selected><fmt:message key="chooseTheme" /></option>
                        <option value="${Theme.SCIENCE}"><fmt:message key="science" /></option>
                        <option value="${Theme.NATURE}"><fmt:message key="nature" /></option>
                        <option value="${Theme.ART}"><fmt:message key="art" /></option>
                    </select>
                </div>
                <div class="input-field">
                    <select name="${Jsp.PUBLISHING_HOUSE_TYPE}">
                        <option value="" disabled selected><fmt:message key="chooseType" /></option>
                        <option value="${Type.NEWSPAPER}"><fmt:message key="newspaper" /></option>
                        <option value="${Type.MAGAZINE}"><fmt:message key="magazine" /></option>
                        <option value="${Type.SCIENTIFIC_JOURNAL}"><fmt:message key="scientificJournal" /></option>
                        <option value="${Type.YEARBOOK}"><fmt:message key="yearbook" /></option>
                    </select>
                </div>
                <button class="btn indigo" type="submit"><fmt:message key="create" /></button>
            </form>
        </div>
        <div class="col m5 s12 offset-m1">
            <form method="post" action="${pageContext.request.contextPath}${Paths.REMOVE_PUBLICATION_HOUSE}">
                <h5>
                    <em><fmt:message key="deletePublicationWithId" /></em>
                </h5>
                <input type="number" min="1" placeholder="Id of publishing house to delete" name="${Jsp.DELETE_ID}">
                <button class="btn indigo" type="submit"><fmt:message key="delete" /></button>
            </form>
        </div>
    </div>
</section>

<jsp:include page="components/footer.jsp" />

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<script>
    $(document).ready(function(){
        $('.sidenav').sidenav();
        $('.dropdown-trigger').dropdown();
        $('.modal').modal();
        $('select').formSelect();
    });
</script>
</body>
</html>
