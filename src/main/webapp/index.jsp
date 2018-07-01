<%@ page import="com.project.chatting.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="assets/css/login.css"/>
    <script src='bootstrap/js/jquery.js'></script>
</head>
<body>
<div class="login-page">

    <div class="form">

        <%
            User user = (User) session.getAttribute("user");
            if (user != null) {
                response.sendRedirect("page/home.jsp");
            }
            String errorMsg = (String) session.getAttribute("errorMsg");
            if (errorMsg != null) {
        %>
        <span style="color: red;">
            <%= errorMsg %></span><br/><br/>
        <%
                session.removeAttribute("errorMsg");
            }
        %>
        <form class="register-form" action="" method="post">
            <input type="text" placeholder="Full Name"/>
            <input type="password" placeholder="Password"/>
            <input type="text" placeholder="Email Address"/>
            <input type="text" placeholder="Contact Number"/>
            <button>create</button>
            <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form>
        <form class="login-form" action="page/login.jsp" method="post">
            <input type="text" placeholder="Email" name="email"/>
            <input type="password" placeholder="Password" id="pass" name="password"/>
            <button id="login">login</button>
            <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form>
        <textarea rows="10" cols="15" id="showTxt">

        </textarea>
        <input type="text" id="msg">
        <button id="send" onclick="sendMessage(msg.value)">send</button>

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
    var webSocket = new WebSocket("ws://localhost:8080/chat");
    var show = document.getElementById("showTxt");
    webSocket.onopen = function (ev) {
        processOpen(ev);
    };
    webSocket.onclose = function (ev) {
        processClose(ev);
    };
    webSocket.onmessage = function (ev) {
        processMessage(ev);
    };
    webSocket.onerror = function (ev) {
        processError(ev);
    };

    function processOpen(msg) {
        show.value = "Server Opened : " + msg;
    }

    function processMessage(msg) {
        show.value = "On Message : " + msg.data;
    }

    function processClose(msg) {
        webSocket.send("client disconnected !");
        show.value = "Server Closed : " + msg;
    }

    function processError(msg) {

        show.value = "Connection Error: " + msg;
    }

    function sendMessage(data) {
        webSocket.send(data);
    }
</script>
</body>
</html>