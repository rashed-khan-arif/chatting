<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="assets/css/login.css"/>
    <script src='bootstrap/js/jquery.js'></script>
</head>
<body>
<div class="login-page">
    <div class="form">
        <form class="register-form" method="post">
            <input type="text" placeholder="Full Name"/>
            <input type="password" placeholder="Password"/>
            <input type="text" placeholder="Email Address"/>
            <input type="text" placeholder="Contact Number"/>
            <button>create</button>
            <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form>
        <form class="login-form" action="page/home.jsp"  method="post">
            <input type="text" placeholder="Email" name="email"/>
            <input type="password" placeholder="Password" id="pass" name="password"/>
            <button id="login">login</button>
            <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form>
    </div>
</div>
<script>
    $('.message a').click(function () {
        $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });
    $('#login').click(function () {
        var passLength = $('#pass').val();
        if (passLength.length < 6) {
            alert("Password length is too short !");
            return false;
        }
    });
</script>
</body>
</html>