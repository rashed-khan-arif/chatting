<%@ page import="com.project.chatting.dao.Dao" %>
<%@ page import="com.project.chatting.dao.impl.DAOImpl" %>
<%@ page import="com.project.chatting.model.User" %>
<%@ page import="com.project.chatting.core.Encryption" %><%--
  Created by IntelliJ IDEA.
  User: arifk
  Date: 3.7.18
  Time: 12:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%
    String fullName = (String) request.getParameter("fullName");
    String email = (String) request.getParameter("email");
    String password = (String) request.getParameter("password");
    String contactNumber = (String) request.getParameter("contactNumber");
    User user = new User();
    user.setFullName(fullName);
    user.setEmail(email);
    user.setContactNumber(Integer.parseInt(contactNumber));
    user.setPassword(Encryption.getStringToMd5Value(password));
    Dao dao = new DAOImpl();
    if (!dao.getUserDao().isEmailUnique(email)) {
        session.setAttribute("errorMsg", "Email already exits ! Use another one !");
        response.sendRedirect("../index.jsp");
    } else {
        User createdUser = dao.getUserDao().addUser(user);
        if (createdUser == null) {
            session.setAttribute("errorMsg", "Invalid username or password !");
            response.sendRedirect("../index.jsp");
        } else {
            session.setAttribute("user", user);
            response.sendRedirect("home.jsp");
        }
    }
%>
