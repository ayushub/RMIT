/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa.controller;

import com.acme.jpa.SavingsTransactions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yordan
 */
@Stateless
public class SavingsTransactionsFacade extends AbstractFacade<SavingsTransactions> {
    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SavingsTransactionsFacade() {
        super(SavingsTransactions.class);
    }
    
}
