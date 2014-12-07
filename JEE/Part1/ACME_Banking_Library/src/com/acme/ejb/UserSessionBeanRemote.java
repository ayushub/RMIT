/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import javax.ejb.Remote;

/**
 *
 * @author Yordan
 */
@Remote
public interface UserSessionBeanRemote {

    public Boolean createUser(String userId, String password, String userName, String userType);

    public String authenticateUser(String userId, String password);
    
}
