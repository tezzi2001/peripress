<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="content"/>

<footer class="page-footer grey darken-3">
    <div class="container">
        <div class="row">
            <div class="col l3 s10 offset-s2">
                <h5><fmt:message key="about" /></h5>
                <p class="grey-text text-lighten-3"><fmt:message key="designedFor" /> <a class="grey-text text-lighten-3" href="https://www.epam.com/">EPAM Systems</a> <fmt:message key="terms" /></p>
            </div>
            <div class="col l3 offset-l2 s4 offset-s2">
                <h5><fmt:message key="product" /></h5>
                <ul>
                    <li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/register"><fmt:message key="startForFree" /></a></li>
                    <li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/login"><fmt:message key="signIn" /></a></li>
                </ul>
            </div>
            <div class="col l3 offset-l1 s4 offset-s2">
                <h5 class="white-text"><fmt:message key="connect" /></h5>
                <ul>
                    <li><a class="grey-text text-lighten-3" href="#">Telegram</a></li>
                    <li><a class="grey-text text-lighten-3" href="#">GitHub</a></li>
                    <li><a class="grey-text text-lighten-3" href="#">Linked In</a></li>
                    <li><a class="grey-text text-lighten-3" href="#">Instagram</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer-copyright grey darken-4">
        <div class="container center-align">&copy; 2020 Peripress</div>
    </div>
</footer>
