<%-- 
    Document   : createEmplooyeePortal
    Created on : 04/10/2012, 8:44:14 PM
    Author     : s3391854
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Employee Portal</title>
    </head>
    <body>
        <h1>Enter Employee Details</h1>
        <form action="CreateEmployeeServlet" method="POST">
        <table border="1" width="70" cellspacing="1" cellpadding="5">
            <thead>
                <tr>
                    <th>Query</th>
                    <th>Detail</th>
                </tr>
            </thead>
            
            <tbody>
                <tr>
                    <td>Given Name</td>
                    <td><input type="text" name="first_name" value="" /></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="last_name" value="" /></td>
                </tr>
                <tr>
                    <td>Date of Birth </td>
                    <td><input type="text" name="dob" value="YYYY-MM-DD" /></td>
                </tr>
                <tr>
                    <td>Residential Address</td>
                    <td><textarea name="address" rows="4" cols="20">
                        </textarea></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Create Employee" /></td>
                </tr>
            </tbody>
        </table>
        </form>
    </body>
</html>
