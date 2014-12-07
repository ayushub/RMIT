/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import com.acme.jpa.controller.EmployeeFacade;
import com.acme.jpa.Employee;
import java.sql.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author s3391854
 */

@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote {
    
    

    @EJB
    private UserSessionBeanRemote userSessionBean;
    @EJB
    private EmployeeFacade employeefacade;

    @Override
    public boolean createemployee( String firstName, String lastName, Date dateOfBirth, String address) {
        boolean isSuccessful = true;

        Employee e = new Employee();
        String employeeId = String.format("E%05d",employeefacade.count() + 1);
        e.setEmployeeId(employeeId);
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setDob(dateOfBirth);
        e.setAddress(address);

        try {
            employeefacade.create(e);
            
            userSessionBean.createUser(employeeId, "pass", firstName + " " + lastName, "Employee");

            System.out.println("Done!");

        } catch (Exception ex) {
            isSuccessful = false;

            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return isSuccessful;
    }
}
