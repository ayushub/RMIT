/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import com.acme.jpa.Customer;
import com.acme.jpa.controller.CustomerFacade;
import java.math.BigDecimal;
import java.sql.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Yordan
 */

@Stateful
public class CustomerSessionBean implements CustomerSessionBeanRemote {

    @EJB
    private UserSessionBeanRemote userSessionBean;
    @EJB
    private CustomerFacade customerFacade;
    
    /**
     * Customer entity (state) to be maintained
     */
    Customer customer;

    @PostConstruct
    private void initialize() {
        customer = new Customer();
    }

    @Override
    public void setPersonalDetails(String firstName, String lastName, Date dateOfBirth, String address) {
//        boolean isSuccessful = true;

        String customerId = String.format("C%07d", customerFacade.count() + 1);

        customer.setCustomerId(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setDob(dateOfBirth);
        customer.setAddress(address);
        
        System.out.println("setPersonalDetails Done!");
        
    }

    @Override
    public void setEmploymentDetails(String currentJob, String companyName, double salaryPerYear) {

        customer.setCurrentJob(currentJob);
        customer.setCompanyName(companyName);
        customer.setSalaryPerYear(BigDecimal.valueOf(salaryPerYear));
        
        System.out.println("setEmploymentDetails Done!");

    }

    @Override
    public void setContactMethod(String contactMethod) {

        customer.setContactMethod(contactMethod);
        
        System.out.println("setContactMethod Done!");

    }

    @Override
    public boolean createCustomer() {
        boolean isSuccessful = true;

        try {
            customerFacade.create(customer);

            /* Create user for this customer */
            userSessionBean.createUser(customer.getCustomerId(), "pass", customer.getFirstName() + " " + customer.getLastName(), "Customer");

            System.out.println("Done!");

        } catch (Exception e) {
            isSuccessful = false;

            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return isSuccessful;
    }
    
//    @Override
//    public Boolean createcustomer(String customerId, String firstName, String lastName, Date dateOfBirth, String address) {
//        boolean isSuccessful = true;
//
//        Customer c = new Customer();
//
//        c.setCustomerId(customerId);
//        c.setFirstName(firstName);
//        c.setLastName(lastName);
//        c.setDob(dateOfBirth);
//        c.setAddress(address);
//
//        try {
//            customerFacade.create(c);
//
//            userSessionBean.createUser(customerId, "pass", firstName + " " + lastName, "Customer");
//
//            System.out.println("Done!");
//
//        } catch (Exception e) {
//            isSuccessful = false;
//
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//        return isSuccessful;
//    }
    
}
