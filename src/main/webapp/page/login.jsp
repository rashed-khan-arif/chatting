<%@ page import="com.project.chatting.dao.Dao" %>
<%@ page import="com.project.chatting.dao.AccountDAO" %>
<%@ page import="com.project.chatting.dao.impl.AccountDAOImpl" %>
<%@ page import="com.project.chatting.dao.impl.DAOImpl" %>
<%@ page import="com.project.chatting.core.Encryption" %>
<%@ page import="com.project.chatting.model.User" %>
<% String username = request.getParameter("email");
    String password = request.getParameter("password");
    AccountDAO accountDAO = new DAOImpl().getAccountDao();
    try {
        User user = accountDAO.checkLoginInfo(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            response.sendRedirect("home.jsp");
        } else {
            session.setAttribute("errorMsg", "Invalid username or password !");
            response.sendRedirect("../index.jsp");
        }
    } catch (Exception ex) {
        response.getOutputStream().println("Database Failed !");
    }
%>