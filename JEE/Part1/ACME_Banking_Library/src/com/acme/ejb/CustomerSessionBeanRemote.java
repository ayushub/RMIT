/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import java.sql.Date;
import javax.ejb.Remote;

/**
 *
 * @author Yordan
 */
@Remote
public interface CustomerSessionBeanRemote {

//    Boolean createcustomer(String customerId, String firstName, String lastName, Date dateOfBirth, String address);
    
    void setPersonalDetails(String firstName, String lastName, Date dateOfBirth, String address);

    void setEmploymentDetails(String currentJob, String companyName, double salaryPerYear);

    void setContactMethod(String contactMethod);
    
    boolean createCustomer();
    
}
