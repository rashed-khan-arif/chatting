<%@ page import="com.project.chatting.model.User" %>
<%@ page import="com.project.chatting.config.Config" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.chatting.model.UserFriend" %>
<%@ page import="com.project.chatting.dao.impl.DAOImpl" %>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
<head>
    <title>TL Chat</title>
    <meta charset='UTF-8'>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,700,300' rel='stylesheet'
          type='text/css'>
    <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">


    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css'>
    <link rel='stylesheet prefetch'
          href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.2/css/font-awesome.min.css'>
    <link rel='stylesheet' href='../assets/css/home.css'>
    <% User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("../index.jsp");
            return;
        } %>
</head>
<body>
<div id="frame">
    <div id="sidepanel">
        <div id="profile">
            <div class="wrap">
                <img id="profile-img" src="../img/avt.png" class="online" alt=""/>

                <p><%= user.getFullName() %>
                </p><br/>

                <p id="user-status"></p>
            </div>
        </div>
        <div id="search">
            <label><i class="fa fa-search" aria-hidden="true"></i></label>
            <input type="text" placeholder="Search contacts..."/>
        </div>
        <div id="contacts">
            <ul style="margin-top: 5px" id="ul-contactList">
                <%
                    List<UserFriend> users = new DAOImpl().getFriendDao().getFriendsByUserId(user.getUserId());
                    for (UserFriend uf : users) { %>
                <li class="contact active" onclick="loadChatPage(true,<%=uf.getFriend().getUserId()%>)">
                    <div class="wrap"><img src="../img/avt-2.png" alt=""/>
                        <div class="meta"><p class="name"><%= uf.getFriend().getFullName() %>
                        </p>
                            <p class="preview"><%=uf.getFriend().getEmail() %>
                            </p>
                        </div>
                    </div>
                </li>
                <%}%>
            </ul>
        </div>
        <div id="bottom-bar">
            <button aria-hidden="true" data-toggle="modal"
                    data-target="#addContact"><i class="fa fa-user-plus fa-fw"></i>
                <span>Add Friend</span>
            </button>
            <button id="settings"><i class="fa fa-cog fa-fw" aria-hidden="true"></i> <span>Settings</span></button>
        </div>
    </div>
    <div class="content">
        <%--<jsp:include page="chat_page.jsp"/>--%>

        <div id="nav-bar">
            <u id="nav-menu">
                <li><i class="glyphicon glyphicon-user"></i>
                    <ul id="friendList">

                    </ul>
                </li>
                <li><span class="badge">4</span><i class="glyphicon glyphicon-bell"></i>
                    <ul>

                    </ul>
                </li>
            </u>
        </div>
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
    <div id="rightPanel">

    </div>
</div>
<div class="modal fade" id="addContact" tabindex="-1" role="dialog" aria-labelledby="addContact"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Add Friend</h5>

            </div>
            <div class="modal-body ">

                <div class="form-group">
                    <label for="email">Email address of your friend </label><br/><br/>
                    <input type="email" name="emailAddress" class="form-control" id="email"
                           aria-describedby="emailHelp"
                           placeholder="Enter email">
                    <small id="emailHelp" class="form-text text-muted">
                    </small>
                </div>
                <br/><br/>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" onclick="sendRequest(email.value)">Send A Friend Request
                </button>

            </div>

        </div>
    </div>
</div>


<script>
    var webSocket = new WebSocket("<%=Config.chatUrl+user.getUserId()%>");
    var show = document.getElementById("showTxt");
    webSocket.onopen = function (ev) {
        processOpen(ev);
    };
    webSocket.onclose = function (ev) {
        processClose(ev);
    };
    webSocket.onmessage = function (ev) {
        processMessage(ev.data);
        console.log(ev.data);
    };
    webSocket.onerror = function (ev) {
        processError(ev);
    };

    function processOpen(msg) {
        var proImage = document.getElementById("profile-img");
        var userStatus = document.getElementById("user-status");
        proImage.style.borderColor = "green";
        userStatus.innerHTML = "Online";
        showFriendRequests();
    }

    function processMessage(msg) {
        console.log(msg);
        var data=JSON.parse(msg);
        switch (data['eventId']) {
            case 1:
                bindFriendRequest((data['data']));
                return;
            case 2:
                return;
            case 3:
                return;
            default:
                return;
        }
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

    function loadChatPage(doLoad, friendId) {
        if (doLoad) {
            $("#chat-area").css("visibility", "visible");
            $("#home-page").css("display", "none");
            getUserDetails(friendId);
        } else {
            $("#chat-area").css("visibility", "hidden");
            $("#home-page").css("display", "block");
        }
    }


    function getUserDetails(friendId) {
        var getUser = "<%= Config.getUserUrl%>" + friendId;
        jQuery.ajax({
            url: getUser,
            type: "GET",
            success: function (item) {
                var data = JSON.parse(item);

                $("#chat-title").html(data["fullName"]);
            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        });
    }

    function showFriendRequests() {
        var userId = parseInt(<%= user.getUserId()%>);
        var api = "<%= Config.friendRequestUrl%>" + userId;
        jQuery.ajax({
            url: api,
            type: "GET",
            success: function (item) {
                var data = JSON.parse(item);
                var list = $('#friendList');
                jQuery.each(data, function (key, value) {
                    list.append(" <li> <img src=\"../img/avt-2.png\" width=\"50px\" height=\"50px\" style=\"float: left\" alt=\"\">\n" +
                        "                            <p>" + value['user'].fullName + "</p>\n" +
                        "                            <div style=\"\">\n" +
                        "                                <p style=\"width: 50%; float: left\" onclick=\"acceptRequest(" + value['userFriendId'] + ")\">Accept</p>\n" +
                        "                                <p style=\"width: 50%;float: left\" onclick=\"rejectRequest(" + value['userFriendId'] + ")\">Reject</p>\n" +
                        "                            </div>\n" +
                        "                        </li>");
                });
            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        });
    }

    function showProfile(friendId) {

    }

    function acceptRequest(userFriendId) {

    }

    function rejectRequest(userFriendId) {

    }

    function bindFriendRequest(value) {
        var list = $('#friendList');
        list.append(" <li> <img src=\"../img/avt-2.png\" width=\"50px\" height=\"50px\" style=\"float: left\" alt=\"\">\n" +
            "                            <p>" + value['user'].fullName + "</p>\n" +
            "                            <div style=\"\">\n" +
            "                                <p style=\"width: 50%; float: left\" onclick=\"acceptRequest(" + value['userFriendId'] + ")\">Accept</p>\n" +
            "                                <p style=\"width: 50%;float: left\" onclick=\"rejectRequest(" + value['userFriendId'] + ")\">Reject</p>\n" +
            "                            </div>\n" +
            "                        </li>");
    }

    function sendRequest(data) {
        var addContactApi = "<%= Config.addContactUrl%>";
        var userId = parseInt(<%=user.getUserId()%>);
        jQuery.ajax({
            url: addContactApi,
            type: "POST",
            data: {'email': data, "userId": userId},
            success: function (item) {
                var msg = document.getElementById("emailHelp");
                $("#emailHelp").css("color", "red");
                msg.innerHTML = item;
            },
            error: function (xhr) {
                $("#emailHelp").innerHTML = xhr;
            }
        });
    }

</script>

<script src='../bootstrap/js/jquery.js'></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>
</body>
</html>