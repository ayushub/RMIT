/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import com.acme.jpa.Account;
import com.acme.jpa.HomeLoan;
import com.acme.jpa.Savings;
import com.acme.jpa.SavingsTransactions;
import com.acme.jpa.controller.AccountFacade;
import com.acme.jpa.controller.CustomerFacade;
import com.acme.jpa.controller.HomeLoanFacade;
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
public class HomeLoanSessionBean implements HomeLoanSessionBeanRemote {

    @EJB
    private SavingsTransactionsFacade savingsTransactionsFacade;
    @EJB
    private HomeLoanFacade homeLoanFacade;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private AccountFacade accountFacade;
    @EJB
    private SavingsFacade savingsFacade;

    @Override
    public String makeDeposit(String customerId, String savingsAccountNumber, String homeLoanAccountNumber, double amount) {

        String message = null;

        /* Customer exists */
        if (customerFacade.getCustomer(customerId) != null) {

            /* Check is Home Loan account
             * Check getting account from Customer.
             */
            Account savingsAccount = accountFacade.getAccount(savingsAccountNumber);

            Account homeLoanAccount = accountFacade.getAccount(homeLoanAccountNumber);

            /* Account exists */
            if (savingsAccount != null && homeLoanAccount != null) {

                /* Biz Logic for Savings Account  */
                Savings savingsRegister = null;

                for (Savings savings : savingsAccount.getSavingsCollection()) {
                    savingsRegister = savings;
                }

                BigDecimal balance = savingsRegister.getBalance();

                if (savingsRegister.getBalance().compareTo(BigDecimal.valueOf(amount)) >= 0) {

                    BigDecimal newBalance;
                    newBalance = balance.subtract((BigDecimal.valueOf(amount)));

                    savingsRegister.setBalance(newBalance);

                    /* Withdrawals should be a negative amount */
                    BigDecimal withdrawalAmount;
                    withdrawalAmount = BigDecimal.valueOf(-1 * amount);

                    /* Create Savings Transaction entity */
                    SavingsTransactions savingsTx = new SavingsTransactions();

                    savingsTx.setAccountKey(savingsAccount);
                    savingsTx.setCategory("Withdrawal");
                    savingsTx.setDescription("Deposit to Home Loan Account");
                    savingsTx.setAmount(withdrawalAmount);

                    /* Biz Logic for Home Loan */
                    HomeLoan homeLoanRegister = null;

                    for (HomeLoan homeLoan : homeLoanAccount.getHomeLoanCollection()) {
                        homeLoanRegister = homeLoan;
                    }

                    BigDecimal amountRepayed = homeLoanRegister.getAmountRepayed();
                    BigDecimal newAmountRepayed;

                    amountRepayed = (amountRepayed == null) ? BigDecimal.ZERO : amountRepayed;

                    newAmountRepayed = amountRepayed.add(BigDecimal.valueOf(amount));

                    homeLoanRegister.setAmountRepayed(newAmountRepayed);

                    try {

                        savingsFacade.edit(savingsRegister);

                        savingsTransactionsFacade.create(savingsTx);

                        homeLoanFacade.edit(homeLoanRegister);

                        System.out.println(message = "Done!");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    message = "Account does not have enough funds.";
                }

            } else {
                message = "Account(s) do(es) not exist.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;

    }
}
