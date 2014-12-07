/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import com.acme.jpa.User;
import com.acme.jpa.controller.AbstractFacade;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yordan
 */
@Stateless
@LocalBean
public class UserSessionBean extends AbstractFacade<User> implements UserSessionBeanRemote {

    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    //<editor-fold defaultstate="collapsed" desc="comment">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserSessionBean() {
        super(User.class);
    }
    //</editor-fold>

    @Override
    public Boolean createUser(String userId, String password, String userName, String userType) {
        boolean isSuccessful = true;

        User user = new User();

        user.setUserId(userId);
        user.setUserPassword(password);
        user.setUserName(userName);
        user.setUserType(userType);
        user.setStatus('A');

        try {
            this.create(user);

            System.out.println("Done!");

        } catch (Exception e) {
            isSuccessful = false;

            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return isSuccessful;

    }

    @Override
    public String authenticateUser(String userId, String password) {
        String status = "Ok";

        User user = getUserById(userId);

        /* User exists */
        if (user != null) {

            if (user.getUserPassword().equals(password)) {
                
                user.setStatus('L');
                
                this.edit(user);            
                
                System.out.println("Done!");             
                
                
            } else {
                status = "Wrong User or Password";
            }

        } else {
            status = "Wrong User or Password";            
        }
        
        return status;
    }

    /* Custom method to get Entity */
    public User getUserById(String userId) {

        //int userKey = 0;
        User user = null;

        TypedQuery<User> query =
                em.createNamedQuery("User.findByUserId", User.class);

        query.setParameter("userId", userId);

        //userKey = query.getSingleResult().getId();
        //return find(userKey);
        
        try {
            user = query.getSingleResult();
            
        } catch (NoResultException e) {
            
            e.printStackTrace();            
        }         
                
        return user;

    }
}
