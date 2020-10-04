<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.bondarenko.constant.Paths" %>
<%@ page import="org.bondarenko.constant.Jsp" %>

<!DOCTYPE html>
<html lang="" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Peripress | Home</title>
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form-colors.css">
    <style>
        nav {
            position: fixed;
            backdrop-filter: blur(5px);
        }
        #logo {
            height: 40px;
            margin: 12px;
        }
        #sign-in {
            margin-left: 10px;
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

<jsp:include page="components/guestHeader.jsp" />

<section class="container section">
    <div class="row" style="margin-top: 100px">
        <div class="col s12 l4">
            <img class="responsive-img" src="img/person-with-smartphone-and-laptop.jpg" alt="person using smartphone and laptop at the same time">
        </div>
        <div class="col s12 l7 offset-l1">
            <h3 class="indigo-text text-darken-4 center-align"><strong>Read periodicals with Peripress</strong></h3>
            <p class="center-align">Subscribe and get press all over the world. There is no need for carrying a lot of paper now!</p>
            <p class="center-align" style="margin-top: 30px">
                <a class="indigo darken-4 white-text btn-large" href="${pageContext.request.contextPath}${Paths.REGISTER}" style="margin-right: 35px">Get account</a>
                <a class="white indigo-text text-darken-4 btn-large" href="${pageContext.request.contextPath}${Paths.LOGIN}">I have account</a>
            </p>
        </div>
    </div>

    <div class="row" style="margin-top: 100px">
        <div class="col s12 l4">
            <h3 class="indigo-text text-darken-4 center-align"><strong>Huge choice</strong></h3>
            <p class="center-align">We have wide variety of categories and types of periodical. Different filters will help you to find what you need.</p>
        </div>
        <div class="col s12 l7 offset-l1">
            <img src="img/magazines-on-stand.jpg" alt="magazines on stand" class="responsive-img">
        </div>
    </div>

    <div class="row" style="margin-top: 100px; margin-bottom: 100px">
        <div class="col s12 l4">
            <img class="responsive-img" src="img/coins.jpg" alt="person using smartphone and laptop at the same time">
        </div>
        <div class="col s12 l7 offset-l1">
            <h3 class="indigo-text text-darken-4 center-align"><strong>Reasonable prices</strong></h3>
            <p class="center-align">Prices of our digital periodicals is as low as real of one. We even have free press!</p>
        </div>
    </div>
</section>

<section class="section  light-blue lighten-5" style="height: 300px">

</section>

<section class="section container scrollspy" id="contact">
    <div class="row">
        <div class="col s12 l6">
            <h3 class="indigo-text">Subscribe to our newsletter</h3>
            <p>Be the first to learn the news about new features and product updates.</p>
            <p>You will also receive subscribe recommendations for best publishers!</p>
        </div>
        <div class="col s12 l4 offset-l2">
            <form action="${pageContext.request.contextPath}${Paths.GET_NEWSLETTER}" method="post">
                <div class="input-field">
                    <i class="material-icons prefix">email</i>
                    <input type="email" id="email" name="${Jsp.EMAIL}">
                    <label for="email">Your Email</label>
                </div>
                <div class="input-field center">
                    <button class="btn indigo pulse" type="submit">Subscribe</button>
                </div>
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
        $('.modal').modal();
    });
</script>
</body>
</html>
