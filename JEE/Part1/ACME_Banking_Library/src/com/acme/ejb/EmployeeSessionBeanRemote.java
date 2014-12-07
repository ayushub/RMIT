/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import java.sql.Date;
import javax.ejb.Remote;

/**
 *
 * @author s3391854
 */
@Remote
public interface EmployeeSessionBeanRemote {
   public boolean createemployee(String firstName, String lastName, Date dateOfBirth, String address);
}
