<%@ page contentType="text/html;charset=UTF-8" %>

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
                <form action="${pageContext.request.contextPath}/sign-in" method="POST" class="center-block" style="text-align: center; min-width: 80%">
                    <h1>Log in</h1>
                    <h4>Welcome back!</h4>
                    <div class="input-field" style="margin-top: 40px">
                        <i class="material-icons prefix">account_circle</i>
                        <input type="text" id="username" name="username">
                        <label for="username">Your username</label>
                    </div>
                    <div class="input-field">
                        <i class="material-icons prefix">lock</i>
                        <input type="password" id="password" name="password">
                        <label for="password">Your Password</label>
                    </div>
                    <p>Don`t have an account yet? <a href="${pageContext.request.contextPath}/sign-up">Register</a></p>
                    <p>Forgot password? <a class="modal-trigger" href="#reset-password">Reset password</a></p>
                    <div class="input-field center">
                        <button class="btn-large indigo darken-4" type="submit">Log in</button>
                    </div>
                </form>
                <a style="position: absolute; bottom: 2%" href="${pageContext.request.contextPath}/">Go home</a>
            </div>
        </div>
    </div>
</div>

<div class="modal row" id="reset-password" style="margin-top: 7%">
    <div class="modal-content col s12 m6 offset-m3" style="text-align: center">
        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <h3>Reset password</h3>
            <p>The following instructions will be sent on your email</p>
            <div class="input-field center">
                <i class="material-icons prefix">email</i>
                <input type="email" id="email">
                <label for="email">Enter your Email</label>
            </div>
            <div class="input-field center">
                <button class="btn-large indigo darken-4" type="submit">Reset password</button>
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

