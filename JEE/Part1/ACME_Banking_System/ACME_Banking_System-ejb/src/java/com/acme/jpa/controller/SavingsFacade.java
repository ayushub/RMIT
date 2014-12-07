/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa.controller;

import com.acme.jpa.Account;
import com.acme.jpa.Savings;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yordan
 */
@Stateless
public class SavingsFacade extends AbstractFacade<Savings> {

    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SavingsFacade() {
        super(Savings.class);
    }

    /* Custom method to get Entity */
//    public Savings getSavingsAccount(String accountKey) {
//
//        TypedQuery<Savings> query =
//                em.createNamedQuery("Savings.findAll", Savings.class);
//
//        for (Savings account : query.getResultList()) {
//            
//        }
//
//        return find(accountKey);
//
//    }
}
