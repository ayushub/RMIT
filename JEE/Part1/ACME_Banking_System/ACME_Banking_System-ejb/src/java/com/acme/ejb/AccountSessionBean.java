/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import com.acme.jpa.Account;
import com.acme.jpa.CreditCard;
import com.acme.jpa.Customer;
import com.acme.jpa.HomeLoan;
import com.acme.jpa.Savings;
import com.acme.jpa.controller.AccountFacade;
import com.acme.jpa.controller.CreditCardFacade;
import com.acme.jpa.controller.CustomerFacade;
import com.acme.jpa.controller.HomeLoanFacade;
import com.acme.jpa.controller.SavingsFacade;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Yordan
 */
@Stateless
public class AccountSessionBean implements AccountSessionBeanRemote {

    @EJB
    private HomeLoanFacade homeLoanFacade;
    @EJB
    private CreditCardFacade creditCardFacade;
    @EJB
    private SavingsFacade savingsFacade;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private AccountFacade accountFacade;

    @Override
    public String createSavingsAccount(String customerId, String accountNumber) {

        String message = null;

        Customer customer = customerFacade.getCustomer(customerId);

        /* Customer exists */
        if (customer != null) {

            /* A maximum of five accounts may be opened by each customer. */
            if (customer.getAccountCollection().size() < 5) {

                Account account = new Account();

                account.setCustomerKey(customer);
                //account.setCustomerKey(customerFacade.getCustomer(customerId));
                account.setAccountNumber(accountNumber);
                account.setAccountType("Savings");
                account.setStatus('I');

                try {

                    /* Register account */
                    accountFacade.create(account);

                    /* Create Savings entity */
                    Savings saving = new Savings();
                    saving.setAccountKey(account);

                    savingsFacade.create(saving);

                    System.out.println(message = "Done!");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

            } else {
                message = "A maximum of five accounts may be opened by each customer.";
            }

        } else {
            message = "Customer does not exist.";
        }


        return message;

    }

    @Override
    public String createCreditCardAccount(String customerId, String accountNumber, String creditcardnumber) {

        String message = null;

        Customer customer = customerFacade.getCustomer(customerId);

        /* Customer exists */
        if (customer != null) {
            /* 
             * A maximum of five accounts may be opened by each customer .
             */
            if (customer.getAccountCollection().size() < 5) {
                //if (accountFacade.count() < 5) {
                /*
                 * The Savings account must be the first account a customer opens.
                 */
                if (customerFacade.hasSavingsAccount(customerId)) {

                    Account account = new Account();

                    account.setCustomerKey(customer);
                    //account.setCustomerKey(customerFacade.getCustomer(customerId));
                    account.setAccountNumber(accountNumber);
                    account.setAccountType("CreditCard");
                    account.setStatus('A');

                    try {
                        /* Register account */
                        accountFacade.create(account);

                        /* Create Credit Card entity */
                        CreditCard creditCard = new CreditCard();

                        creditCard.setAccountKey(account);
                        creditCard.setCreditCardNumber(creditcardnumber);
                        creditCard.setCreditLimit(BigDecimal.valueOf(2000));
                        creditCard.setCredit(BigDecimal.ZERO);

                        creditCardFacade.create(creditCard);

                        System.out.println(message = "Done!");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    message = "The Savings account must be the first account a customer opens.";
                }

            } else {
                message = "A maximum of five accounts may be opened by each customer.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;
    }

    @Override
    public String createHomeLoanAccount(String customerId, String accountNumber, double amountBorrowed) {
        String message = null;

        Customer customer = customerFacade.getCustomer(customerId);

        /* Customer exists */
        if (customer != null) {
            /* 
             * A maximum of five accounts may be opened by each customer .
             */
            if (customer.getAccountCollection().size() < 5) {
                /*
                 * The Savings account must be the first account a customer opens.
                 */
                if (customerFacade.hasSavingsAccount(customerId)) {
                    
                    System.out.println("Customer has Savings Accounts");
                    System.out.println(customerFacade.getDepositsToSavingsAccount(customerId));

                    /* 
                     * Only accept three or more deposits into their savings account 
                     */
                    if (customerFacade.getDepositsToSavingsAccount(customerId) >= 3) {

                        Account account = new Account();

                        account.setCustomerKey(customer);
                        account.setAccountNumber(accountNumber);
                        account.setAccountType("Homeloan");
                        account.setStatus('A');

                        try {
                            /* Register account */
                            accountFacade.create(account);

                            /* Create Home Loan entity */
                            HomeLoan homeLoan = new HomeLoan();

                            homeLoan.setAccountKey(account);
                            homeLoan.setAmountBorrowed(BigDecimal.valueOf(amountBorrowed));
                            homeLoan.setAmountRepayed(BigDecimal.ZERO);

                            homeLoanFacade.create(homeLoan);

                            System.out.println(message = "Done!");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        message = "Customer must have three or more deposits into their Savings Account as the proof of income.";
                    }

                } else {
                    message = "The Savings account must be the first account a customer opens.";
                }

            } else {
                message = "A maximum of five accounts may be opened by each customer.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;

    }
}
