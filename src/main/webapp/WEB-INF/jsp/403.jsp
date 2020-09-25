<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>Peripress | Forbidden</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.png" type="image/png">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@600;900&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/4b9ba14b0f.js" crossorigin="anonymous"></script>
    <style>
        body{
            background: #3f51b5;
        }
        .main-box {
            margin: auto;
            height: 600px;
            width: 500px;
            position: relative;
        }
        .err {
            color: #ffffff;
            font-family: 'Nunito Sans', sans-serif;
            font-size: 11rem;
            position:absolute;
            left: 20%;
            top: 8%;
        }
        .far {
            position: absolute;
            font-size: 8.5rem;
            left: 42%;
            top: 17%;
            color: #ffffff;
        }
        .err2 {
            color: #ffffff;
            font-family: 'Nunito Sans', sans-serif;
            font-size: 11rem;
            position:absolute;
            left: 68%;
            top: 8%;
        }
        .msg {
            color: white;
            text-align: center;
            font-family: 'Nunito Sans', sans-serif;
            font-size: 1.6rem;
            position:absolute;
            left: 16%;
            top: 45%;
            width: 75%;
        }
        a {
            text-decoration: none;
            color: #90caf9;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="main-box">
    <div class="err">4</div>
    <i class="far fa-question-circle fa-spin"></i>
    <div class="err2">3</div>
    <div class="msg">You do not have permission to visit this page<p>Let's go <a href="${pageContext.request.contextPath}/">home</a>.</p></div>
</div>
</body>
</html>
