<% String username = request.getParameter("email");
    String password = request.getParameter("password");
    if (username == null || password == null) {
        response.sendRedirect("../index.jsp");
    } else if ((username.equals("a@a.com") && password.equals("123456"))) {
        session.setAttribute("username", username);
        response.sendRedirect("home.jsp");
    } else response.sendRedirect("Error.jsp"); %>