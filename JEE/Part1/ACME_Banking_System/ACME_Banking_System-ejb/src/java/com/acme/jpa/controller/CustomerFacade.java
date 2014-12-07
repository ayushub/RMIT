/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa.controller;

import com.acme.jpa.Account;
import com.acme.jpa.Customer;
import com.acme.jpa.SavingsTransactions;
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
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    /* Custom method to get Entity */
    public Customer getCustomer(String customerId) {

        Customer customer = null;

        //int customerKey = 0;

        TypedQuery<Customer> query =
                em.createNamedQuery("Customer.findByCustomerId", Customer.class);

        query.setParameter("customerId", customerId);

        //customerKey = query.getSingleResult().getId();

        //return find(customerKey);

        try {
            customer = query.getSingleResult();

        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return customer;

    }

    /* Custom method to determine whether customerId has opened Savings accounts */
    public boolean hasSavingsAccount(String customerId) {

        boolean retunValue = false;

//        int customerKey = 0;

        TypedQuery<Customer> query =
                em.createNamedQuery("Customer.findByCustomerId", Customer.class);

        query.setParameter("customerId", customerId);

//        customerKey = query.getSingleResult().getId();            
//        Customer c = find(customerKey);

        try {
            Customer c = query.getSingleResult();

            for (Account a : c.getAccountCollection()) {
                if (a.getAccountType().equals("Savings")) {
//                if (a.getStatus() == 'A') {
                    retunValue = true;
                    break;
//                }
                }
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return retunValue;

    }

    /* Custom method to compute the number of deposits a customer has made into their savings accounts */
    public int getDepositsToSavingsAccount(String customerId) {

        int depositsCount = 0;

        TypedQuery<Customer> query =
                em.createNamedQuery("Customer.findByCustomerId", Customer.class);

        query.setParameter("customerId", customerId);

        try {
            Customer c = query.getSingleResult();

            for (Account a : c.getAccountCollection()) {
                if (a.getAccountType().equals("Savings")) {
                    for (SavingsTransactions tx : a.getSavingsTransactionsCollection()) {
                        if (tx.getCategory().equals("Deposit")) {
                            depositsCount++;
                        }
                    }
                }
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return depositsCount;

    }
}
