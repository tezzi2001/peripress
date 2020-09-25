<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Peripress | Home</title>
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/img/logo.png">
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
    </style>
</head>

<body>
<header>
    <nav class="nav-wrapper transparent">
        <div class="container">
            <a href="${pageContext.request.contextPath}/" class="brand-logo indigo-text text-darken-4">
                <img id="logo" src="img/logo.png" alt="logo">
            </a>
            <a href="#" class="sidenav-trigger" data-target="mobile-menu">
                <i class="material-icons indigo-text text-darken-4">menu</i>
            </a>
            <ul class="right hide-on-med-and-down">
                <li><a class="indigo-text text-darken-4" href="#">Pricing</a></li>
                <li><a class="indigo-text text-darken-4" href="#">Features</a></li>
                <li><a class="indigo-text text-darken-4" href="#">About us</a></li>
                <li><a class="indigo-text text-darken-4" href="#">Contact us</a></li>
                <li><a id="sign-in" class="indigo-text text-darken-4" href="${pageContext.request.contextPath}/sign-in">Sign in</a></li>
                <li><a class="indigo darken-4 white-text btn" href="${pageContext.request.contextPath}/sign-up">Start for free</a></li>
            </ul>
        </div>
    </nav>

    <div>
        <ul class="sidenav indigo lighten-5" id="mobile-menu">
            <li><h3 class="center-align">Peripress</h3></li>
            <hr>
            <li><a href="#">Pricing</a></li>
            <li><a href="#">Features</a></li>
            <li><a href="#">About us</a></li>
            <li><a href="${pageContext.request.contextPath}/sign-in">Sign in</a></li>
            <li><a href="${pageContext.request.contextPath}/sign-up">Start for free</a></li>
        </ul>
    </div>
</header>

<section class="container section">
    <div class="row" style="margin-top: 100px">
        <div class="col s12 l4">
            <img class="responsive-img" src="img/person-with-smartphone-and-laptop.jpg" alt="person using smartphone and laptop at the same time">
        </div>
        <div class="col s12 l7 offset-l1">
            <h3 class="indigo-text text-darken-4 center-align"><strong>Read periodicals with Peripress</strong></h3>
            <p class="center-align">Subscribe and get press all over the world. There is no need for carrying a lot of paper now!</p>
            <p class="center-align" style="margin-top: 30px">
                <a class="indigo darken-4 white-text btn-large" href="${pageContext.request.contextPath}/sign-up" style="margin-right: 35px">Get account</a>
                <a class="white indigo-text text-darken-4 btn-large" href="${pageContext.request.contextPath}/sign-in">I have account</a>
            </p>
        </div>
    </div>

    <div class="row" style="margin-top: 100px">
        <div class="col s12 l4">
            <h3 class="indigo-text text-darken-4 center-align"><strong>Huge choice</strong></h3>
            <p class="center-align">We have wide variety of categories and types of periodical. Different filters will help you to find what you need.</p>
        </div>
        <div class="col s12 l7 offset-l1">
            <img src="img/assorted-title-magazines-displayed-on-stand.jpg" alt="" class="responsive-img">
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
            <form>
                <div class="input-field">
                    <i class="material-icons prefix">email</i>
                    <input type="email" id="email">
                    <label for="email">Your Email</label>
                </div>
                <div class="input-field">
                    <p>
                        <label>
                            <input class="with-gap" name="group1" type="radio" checked />
                            <span>Reader</span>
                        </label>
                    </p>
                    <p>
                        <label>
                            <input class="with-gap" name="group1" type="radio" />
                            <span>Publisher</span>
                        </label>
                    </p>
                </div>
                <div class="input-field center">
                    <button class="btn indigo pulse" type="submit">Subscribe</button>
                </div>
            </form>
        </div>
    </div>
</section>

<footer class="page-footer grey darken-3">
    <div class="container">
        <div class="row">
            <div class="col l3 s10 offset-s2">
                <h5>About</h5>
                <p class="grey-text text-lighten-3">This product is designed for <a class="grey-text text-lighten-3" href="https://www.epam.com/">EPAM Systems</a> in terms of EPAM University Java Summer Program 2020</p>
            </div>
            <div class="col l3 offset-l2 s4 offset-s2">
                <h5>Product</h5>
                <ul>
                    <li><a class="grey-text text-lighten-3" href="#">Pricing</a></li>
                    <li><a class="grey-text text-lighten-3" href="#">Features</a></li>
                    <li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/sign-up">Start for free</a></li>
                    <li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/sign-in">Sign in</a></li>
                </ul>
            </div>
            <div class="col l3 offset-l1 s4 offset-s2">
                <h5 class="white-text">Connect</h5>
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
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<script>
    $(document).ready(function(){
        $('.sidenav').sidenav();
    });
</script>
</body>
</html>
