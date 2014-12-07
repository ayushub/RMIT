/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import com.acme.ejb.UserSessionBean;
import com.acme.jpa.User;
import com.acme.jpa.controller.EmployeeFacade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yordan
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    @EJB
    private EmployeeFacade employeeFacade;
    
    

    @EJB
    private UserSessionBean userSessionBean;
    
    
    

//    @EJB
//    private UserSessionBeanRemote userSessionBean;   
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("user_id");
        String password = request.getParameter("password");

        String status = userSessionBean.authenticateUser(userId, password);

        if (status.equals("Ok")) {

            User user = userSessionBean.getUserById(userId);           
           
            
            request.getSession().setAttribute("userName", user.getUserName());

            if (user.getUserType().equals("Customer")) {
                response.sendRedirect("customerPortal.jsp");
            } else {
                response.sendRedirect("employeePortal.jsp");
            }

            //request.getRequestDispatcher("customerPortal.jsp").forward(request, response);

        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
//             TODO output your page here
                out.println("<html>");
                out.println("<head>");
                out.println("<title>LoginServlet Authentication Error</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
                out.println("<div>" + status + "</div>");
                out.println("</body>");
                out.println("</html>");

            } finally {
                out.close();
            }
        }


    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
