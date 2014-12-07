/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa.controller;

import com.acme.jpa.Account;
import com.acme.jpa.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yordan
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    /* Custom method to get Entity
     * Assumption: accountNumber is unique
     */
    public Account getAccount(String accountNumber) {

        Account account = null;

//        int accountKey = 0;

        TypedQuery<Account> query =
                em.createNamedQuery("Account.findByAccountNumber", Account.class);

        query.setParameter("accountNumber", accountNumber);

//        accountKey = query.getSingleResult().getId();
//
//        return find(accountKey);

        try {
            account = query.getSingleResult();

        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return account;

    }

    /*
     * Custom method to get Account entities by customerId (replace by customer.getAccountCollection())
     */
//    public List getAccountsByCustomerId(String customerId) {
//
//        List list = new ArrayList();
//
////        int customerKey = 0;
//        Customer customer;
//
//        TypedQuery<Customer> customerQuery =
//                em.createNamedQuery("Customer.findByCustomerId", Customer.class);
//
//        customerQuery.setParameter("customerId", customerId);
//
////        customerKey = customerQuery.getSingleResult().getId();
//
//        customer = customerQuery.getSingleResult();
//
//        TypedQuery<Account> accountQuery =
//                em.createNamedQuery("Account.findByCustomerKey", Account.class);
//
//        accountQuery.setParameter("customerKey", customer);
//
//        try {
//            list = accountQuery.getResultList();
//
//        } catch (NoResultException e) {
//
//            e.printStackTrace();
//        }
//
//        return list;
//
//    }
}
