/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa.controller;

import com.acme.jpa.CreditCardTransaction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yordan
 */
@Stateless
public class CreditCardTransactionFacade extends AbstractFacade<CreditCardTransaction> {
    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CreditCardTransactionFacade() {
        super(CreditCardTransaction.class);
    }
    
//    public boolean hasTransactions(int creditCardKey){
////        int transactionExists = 0;
//        BigDecimal amt;
//        TypedQuery<CreditCardTransaction> query =
//                em.createNamedQuery("CreditCardTransaction.findAll", CreditCardTransaction.class);
//        query.setParameter("creditCardKey", creditCardKey);
//        for (CreditCardTransaction transactions : query.getResultList()) {
//            amt = transactions.getAmount();
//            if(amt.compareTo((BigDecimal.valueOf(0))) != 0){
//                return true;
//            }
//        }
//
//        return false;
//    }
}
