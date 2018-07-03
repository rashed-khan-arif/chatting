<%@ page import="com.project.chatting.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="assets/css/login.css"/>
    <script src='bootstrap/js/jquery.js'></script>
    <script src='assets/js/chatting.js'></script>
</head>

<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
        response.sendRedirect("page/home.jsp");
    }
%>

<body>
<div class="login-page">

    <div class="form">
        <%

            String errorMsg = (String) session.getAttribute("errorMsg");
            if (errorMsg != null) {
        %>
        <span style="color: red;">
            <%= errorMsg %></span><br/><br/>
        <% session.removeAttribute("errorMsg");
        } %>
        <form class="register-form" action="page/register.jsp" method="post">
            <p id="show"></p><br/><br/>
            <input type="text" placeholder="Full Name" name="fullName" id="fullName"/>
            <input type="password" name="password" placeholder="Password" id="password"/>
            <input type="password" placeholder="Confirm Password" id="cpassword"/>
            <input type="text" placeholder="Email Address" id="email" name="email"/>
            <input type="text" placeholder="Contact Number" id="contactNumber" name="contactNumber"/>
            <button id="create">create</button>
            <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form>
        <form class="login-form" action="page/login.jsp" method="post">
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
    $('#create').click(function () {
        var show = document.getElementById("show");
        var contactNumber = $('#contactNumber').val();
        var fullName = $('#fullName').val();
        var email = $('#email').val();
        var password = $('#password').val();
        var cpassword = $('#cpassword').val();

        if (fullName.trim().length <= 0) {
            show.innerHTML = "Full name is required !";

            return false;
        }

        if (email.trim().length <= 0 || !validateEmail(email)) {
            show.innerHTML = "Please use a valid email address !";

            return false;
        }
        if (password.trim().length < 6) {
            show.innerHTML = "Password at least 6 characters!";

            return false;
        }
        if (cpassword.trim().length < 6) {
            show.innerHTML = "Password at least 6 characters!";

            return false;
        }
        if (cpassword.trim() !== password.trim()) {
            show.innerHTML = "Confirm Password doesn't match !";

            return false;
        }
        if (isNaN(contactNumber)) {
            show.innerHTML = "Please enter numeric value !";
            return false;
        }
    });

</script>
</body>
</html>