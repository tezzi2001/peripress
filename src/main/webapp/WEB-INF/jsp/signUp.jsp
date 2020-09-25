<%@ page contentType="text/html;charset=UTF-8" %>

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
                <form action="${pageContext.request.contextPath}/sign-up" method="POST" class="center-block" style="text-align: center">
                    <h1>Register</h1>
                    <h4>Join to our community now!</h4>
                    <div class="input-field" style="margin-top: 40px">
                        <i class="material-icons prefix">account_circle</i>
                        <input type="text" id="username">
                        <label for="username">Your username</label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">email</i>
                        <input type="email" id="email">
                        <label for="email">Your Email</label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <input type="password" id="password">
                        <label for="password">Your Password</label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">vpn_key</i>
                        <input type="password" id="confirm-password">
                        <label for="confirm-password">Your Password</label>
                    </div>
                    <div class="left-align" style="margin-left: 1vh">
                        <p><strong>Choose account type:</strong></p>
                        <div class="input-field">
                            <p>
                                <label>
                                    <input class="with-gap" name="group1" type="radio" checked />
                                    <span>Reader account</span>
                                </label>
                            </p>
                            <p>
                                <label>
                                    <input class="with-gap" name="group1" type="radio" />
                                    <span>Publisher account</span>
                                </label>
                            </p>
                        </div>
                    </div>
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/sign-in">Log in</a></p>
                    <div class="input-field center">
                        <button class="btn-large indigo darken-4" type="submit">Create an account</button>
                    </div>
                </form>
                <a style="position: absolute; bottom: 2%" href="${pageContext.request.contextPath}/">Go home</a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>

