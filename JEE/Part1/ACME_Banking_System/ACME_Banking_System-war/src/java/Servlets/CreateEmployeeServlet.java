/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import com.acme.ejb.EmployeeSessionBeanRemote;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author s3391854
 */
@WebServlet(name = "CreateEmployeeServlet", urlPatterns = {"/CreateEmployeeServlet"})
public class CreateEmployeeServlet extends HttpServlet {
    @EJB
    private EmployeeSessionBeanRemote employeeSessionBean;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date d = Date.valueOf(request.getParameter("dob"));
        boolean msg = employeeSessionBean.createemployee(request.getParameter("first_name"), request.getParameter("last_name"), d , request.getParameter("address"));
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        if(msg){
        try {
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CreateEmployeeServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Employee Created Successfully</h1>");
            out.println("</body>");
            out.println("</html>");
            
        } finally {            
            out.close();
        }   
           
        } else {
                    try {
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CreateEmployeeServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Sorry</h1>");
            out.println("</body>");
            out.println("</html>");
            
        } finally {            
            out.close();
        }   

        }
        
        
    }


    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
