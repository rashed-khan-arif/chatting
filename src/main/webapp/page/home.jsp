<%@ page import="com.project.chatting.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chatting</title>
    <meta charset='UTF-8'>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,700,300' rel='stylesheet'
          type='text/css'>
    <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src='../bootstrap/js/jquery.js'></script>
    <script src="../bootstrap/js/bootstrap.min.js"></script>

    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css'>
    <link rel='stylesheet prefetch'
          href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.2/css/font-awesome.min.css'>
    <link rel='stylesheet' href='../assets/css/home.css'>

</head>
<body>
<div id="frame">
    <div id="sidepanel">
        <div id="profile">
            <div class="wrap">
                <img id="profile-img" src="../img/avt.png" class="online" alt=""/>
                <% User user = (User) session.getAttribute("user");
                    if (user != null) { %>
                <p><%= user.getFullName() %>
                </p>
                <% } %>
            </div>

        </div>
        <div id="search">
            <label><i class="fa fa-search" aria-hidden="true"></i></label>
            <input type="text" placeholder="Search contacts..."/>
        </div>
        <div id="contacts">
            <ul style="margin-top: 5px">
                <li class="contact active">
                    <div class="wrap">
                        <img src="../img/avt-2.png" alt=""/>
                        <div class="meta">
                            <p class="name">Abdus Salam</p>
                            <p class="preview">Thank you Rashed khan arif </p>
                        </div>
                    </div>
                </li>

            </ul>
        </div>
        <div id="bottom-bar">
            <button id="addcontact"><i class="fa fa-user-plus fa-fw" aria-hidden="true"></i> <span>Add contact</span>
            </button>
            <button id="settings"><i class="fa fa-cog fa-fw" aria-hidden="true"></i> <span>Settings</span></button>
        </div>
    </div>
    <div class="content">
        <div class="contact-profile">
            <img src="../img/avt-3.png" alt=""/>
            <p>Rashed Khan Arif</p>

        </div>
        <div class="messages">
            <ul>
                <li class="sent">
                    <img src="../img/avt.png" alt=""/>
                    <p>Hello salam how are you ? how is you days going ? mine is fine .</p>
                </li>
                <li class="replies">
                    <img src="../img/avt-2.png" alt=""/>
                    <p>Yeah it's good . Spending a silent charming moment with my partner.</p>
                </li>
                <li class="sent">
                    <img src="../img/avt-3.png" alt=""/>
                    <p>That's good .</p>
                </li>

            </ul>
        </div>
        <div class="message-input">
            <div class="wrap">
                <input type="text" placeholder="Write your message..."/>
                <i class="fa fa-paperclip attachment" aria-hidden="true"></i>
                <button class="submit"><i class="fa fa-paper-plane" aria-hidden="true"></i></button>
            </div>
        </div>
    </div>
</div>
<script>
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
        var proImage = document.getElementById("profile-img");
        proImage.style.borderColor="green";
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