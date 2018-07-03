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
    <% User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("../index.jsp");
        } %>
</head>
<body>
<div id="frame">
    <div id="sidepanel">
        <div id="profile">
            <div class="wrap">
                <img id="profile-img" src="../img/avt.png" class="online" alt=""/>
                <%
                    if (user != null) { %>
                <p><%= user.getFullName() %>
                </p><br/>
                <% } %>
                <p id="user-status"></p>
            </div>

        </div>
        <div id="search">
            <label><i class="fa fa-search" aria-hidden="true"></i></label>
            <input type="text" placeholder="Search contacts..."/>
        </div>
        <div id="contacts">
            <ul style="margin-top: 5px" id="ul-contactList">

            </ul>
        </div>
        <div id="bottom-bar">
            <button id="addcontact"><i class="fa fa-user-plus fa-fw" aria-hidden="true"></i> <span>Add contact</span>
            </button>
            <button id="settings"><i class="fa fa-cog fa-fw" aria-hidden="true"></i> <span>Settings</span></button>
        </div>
    </div>
    <div class="content">
        <%--<jsp:include page="chat_page.jsp"/>--%>
        <div id="home-page">
            <p class="text-center">Welcome to <br/><strong>TL</strong> <br/>Chat system</p>
        </div>
        <div style="visibility: hidden;" id="chat-area">
            <div class="contact-profile">
                <img src="../img/avt-3.png" alt=""/>
                <p id="chat-title"></p>
                <span class="glyphicon glyphicon-remove-sign pull-right" style="margin: 20px 20px 0 0"
                      onclick="loadChatPage(false,null)"></span>
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
        var userStatus = document.getElementById("user-status");
        proImage.style.borderColor = "green";
        userStatus.innerHTML = "Online";
        var getContactApi = "http://localhost:8080/getContactList?userId=" +<%=user!=null?user.getUserId():""%>;
        jQuery.ajax({
            url: getContactApi,
            type: "GET",
            success: function (item) {
                jQuery.each(item, function (key, value) {
                    var list = $('#ul-contactList');
                    var frnd=value['friend'];
                    list.append("<li class=\"contact active\" onclick=\" + loadChatPage(true, frnd) \">\n" +
                        "                    <div class=\"wrap\">\n" +
                        "                        <img src=\"../img/avt-2.png\" alt=\"\"/>\n" +
                        "                        <div class=\"meta\">\n" +
                        "                            <p class=\"name\">" + value["friend"].fullName + "</p>\n" +
                        "                            <p class=\"preview\">" + value["friend"].email + " </p>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </li>");
                });
            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        });
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

    function loadChatPage(doLoad, friend) {
        if (doLoad) {
            $("#chat-area").css("visibility", "visible");
            $("#home-page").css("display", "none");
            $("#chat-title").innerHTML = friend.fullName;


        } else {
            $("#chat-area").css("visibility", "hidden");
            $("#home-page").css("display", "block");
        }
    }

    function getMessages(userId) {
        var getMsgApi = "http://localhost:8080/getMessage?userId=" + userId;
        jQuery.ajax({
            url: getMsgApi,
            type: "GET",
            success: function (item) {
                jQuery.each(item, function (key, value) {

                });
            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        });
    }

    function addContact() {
        var addContactApi = "http://localhost:8080/addContact";
        jQuery.ajax({
            url: addContactApi,
            type: "POST",
            data: {},
            success: function (item) {

            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        });

    }

</script>
</body>
</html>