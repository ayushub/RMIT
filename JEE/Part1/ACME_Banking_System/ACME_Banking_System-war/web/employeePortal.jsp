<%-- 
    Document   : employeePortal
    Created on : 20/09/2012, 12:24:40 AM
    Author     : Yordan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Portal Page</title>
    </head>
    <body>
        <h1>Welcome <%= session.getAttribute("userName")%>!
        </h1>
    <center>
        <a href="#"> HOME </a> |
        <a href="#/customermanagement.jsp"> CUSTOMER MANAGEMENT</a> |
        <a href="empmanagement.jsp"> EMPLOYEE MANAGEMENT</a>
        
        
        
    </center>
</body>
</html>
