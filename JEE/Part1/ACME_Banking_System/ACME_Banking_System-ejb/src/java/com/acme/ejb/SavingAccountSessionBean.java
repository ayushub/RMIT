/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import com.acme.jpa.Account;
import com.acme.jpa.Savings;
import com.acme.jpa.SavingsTransactions;
import com.acme.jpa.controller.AccountFacade;
import com.acme.jpa.controller.CustomerFacade;
import com.acme.jpa.controller.SavingsFacade;
import com.acme.jpa.controller.SavingsTransactionsFacade;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Yordan
 */
@Stateless
public class SavingAccountSessionBean implements SavingAccountSessionBeanRemote {

    @EJB
    private SavingsTransactionsFacade savingsTransactionsFacade;
    @EJB
    private SavingsFacade savingsFacade;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private AccountFacade accountFacade;

    @Override
    public String makeDeposit(String customerId, String accountNumber, double amount) {
        String message = null;

        /* Customer exists */
        if (customerFacade.getCustomer(customerId) != null) {

            /* Check is Saving account 
             * Check getting account from Customer.
             */
            Account account = accountFacade.getAccount(accountNumber);

            /* Account exists */
            if (account != null) {

                /* Biz Logic for Savings Account  */
                Savings savingsRegister = null;

                for (Savings savings : account.getSavingsCollection()) {
                    savingsRegister = savings;
                }

                BigDecimal balance = savingsRegister.getBalance();

                BigDecimal newBalance;
                newBalance = ((balance == null) ? BigDecimal.valueOf(amount) : balance.add(BigDecimal.valueOf(amount)));

                savingsRegister.setBalance(newBalance);

                /* Deposits should be a positive amount */
                BigDecimal depositAmount;
                depositAmount = BigDecimal.valueOf(amount);

                /* Create Savings Transaction entity */
                SavingsTransactions savingsTx = new SavingsTransactions();

                savingsTx.setAccountKey(account);
                savingsTx.setCategory("Deposit");
                savingsTx.setDescription("Deposit to Savings Account");
                savingsTx.setAmount(depositAmount);

                try {

//                    System.out.println("Updating Balance...");
                    savingsFacade.edit(savingsRegister);

                    savingsTransactionsFacade.create(savingsTx);

                    /*
                     * Activate account when minimum $50 deposit is made.
                     */
                    if (account.getStatus() == 'I' && savingsRegister.getBalance().compareTo(BigDecimal.valueOf(50.0)) >= 0) {

                        account.setStatus('A');

                        accountFacade.edit(account);

//                        System.out.println("Activating Account...");

                    }

                    System.out.println(message = "Done!");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

            } else {
                message = "Account does not exist.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;

    }

    @Override
    public String makeWithdrawal(String customerId, String accountNumber, double amount) {
        String message = null;

        /* Customer exists */
        if (customerFacade.getCustomer(customerId) != null) {

            /* Check is Saving account 
             * Check getting account from Customer.
             */
            Account account = accountFacade.getAccount(accountNumber);

            /* Account exists */
            if (account != null) {

                /* Account has been activated */
                if (account.getStatus() == 'A') {

                    for (Savings savings : account.getSavingsCollection()) {

                        BigDecimal balance = savings.getBalance();

                        if (savings.getBalance().compareTo(BigDecimal.valueOf(amount)) >= 0) {

                            BigDecimal newBalance;
                            newBalance = balance.subtract((BigDecimal.valueOf(amount)));

                            savings.setBalance(newBalance);

                            /* Withdrawals should be a negative amount */
                            BigDecimal withdrawalAmount;
                            withdrawalAmount = BigDecimal.valueOf(-1 * amount);

                            /* Create Savings Transaction entity */
                            SavingsTransactions savingsTx = new SavingsTransactions();

                            savingsTx.setAccountKey(account);
                            savingsTx.setCategory("Withdrawal");
                            savingsTx.setDescription("Withdrawal from Savings Account");
                            savingsTx.setAmount(withdrawalAmount);

                            try {

                                savingsFacade.edit(savings);

                                savingsTransactionsFacade.create(savingsTx);

                                System.out.println(message = "Done!");

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                            }

                        } else {
                            message = "Account does not have enough funds.";
                        }
                    }

                } else {
                    message = "Account has not been activated. An initial minimum deposit of $50 must be made.";
                }

            } else {
                message = "Account does not exist.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;

    }

    @Override
    public String viewBalance(String customerId, String accountNumber) {
        String message = null;

        /* Customer exists */
        if (customerFacade.getCustomer(customerId) != null) {

            /* Check is Saving account 
             * Check getting account from Customer.
             */
            Account account = accountFacade.getAccount(accountNumber);

            /* Account exists */
            if (account != null) {

                for (Savings savings : account.getSavingsCollection()) {

                    BigDecimal balance = savings.getBalance();

                    message = balance.toString();
                }

            } else {
                message = "Account does not exist.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;
    }
}
