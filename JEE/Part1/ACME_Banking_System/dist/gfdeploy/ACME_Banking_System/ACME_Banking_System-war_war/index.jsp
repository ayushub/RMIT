<%-- 
    Document   : index
    Created on : 06/09/2012, 7:54:23 PM
    Author     : s3391854
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="/ACME_Banking_System-war/EmployeeServlet" method="POST">
            <h6>Enter Employee ID</h6>
            <input type="TEXT" name="EId"/>
            
            <h6>Enter First Name</h6>
            <input type="TEXT" name="FirstName"/>
            
            <h6>Enter Last Name</h6>
            <input type="TEXT" name="LastName"/>
            <input type="Submit" />
        </form>
    </body>
</html>
