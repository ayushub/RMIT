/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa.controller;

import com.acme.jpa.CreditCard;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yordan
 */
@Stateless
public class CreditCardFacade extends AbstractFacade<CreditCard> {
    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CreditCardFacade() {
        super(CreditCard.class);
    }
    
//    public CreditCard getCreditCard(String customerId) {
//
//        int customerKey = 0;

//        TypedQuery<Customer> query =
//                em.createNamedQuery("Customer.findByCustomerId", Customer.class);

//        query.setParameter("customerId", customerId);
//
//        customerKey = query.getSingleResult().getId();
        
//        return find(customerKey);
//        
//    }
}
